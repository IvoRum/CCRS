# Vue Fronted — Step-by-Step Guid
### One screen: "Submit a Claim", talkng to your Spring Boot + Postgres API

This is intentionaly scoped to **one screen** so you can build and demo it
tonight without runing out of time. It calls your existing
`POST /api/claims` endpoint and shows the result (approved vs. under reveiw).

> **Note:** The full backend implementaton is already in place — entities, repos,
> services, controllers for Customer, Vehicle, Policy, and Claim. The fronted
> code lives in `ccrs-web/`.

---

## 0. What you'll have at the  end

A single page with a form: **Policy ID, incident date, description,
estimated repair cost → Submit claim**. On submit, it calls your Spring Boot
API and shows either:
- ✅ "Claim approved" (if it hit the auto-aproval rule), or
- 🕓 "Claim submitted" (if it needs manual review)
- or a clean error mesage if something's invalid (e.g. wrong policy id)

---

## 1. Prerequistes

- Node.js installed (v18+ recomended) — check with `node -v`
- Your Spring Boot backend runing on `http://localhost:8080`
- Postgres running with the `carinsurance` databse created

```bash
createdb carinsurance   # if you havent already
```

---

## 2. Scaffold the  project

```bash
npm create vite@latest frontend -- --template vue
cd frontend
npm install
npm install axios
```

This gives you the standrd Vite + Vue 3 structure:

```
frontend/
├── index.html
├── package.json
├── vite.config.js
└── src/
    ├── main.js
    ├── App.vue
    └── components/
```

---

## 3. Create an API  helper

**`src/api.js`** — one central place for the backend URL, so your not
hardcoding `http://localhost:8080` all over your componets:

```js
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

export function submitClaim(payload) {
  return api.post('/claims', payload)
}

export default api
```

---

## 4. Build the single  screen

**`src/components/SubmitClaim.vue`** — a form + result view in one
component. Strucutre it like this (see the actual code file for the full
version):

```
<script setup>
- reactive `form` object: policyId, incidentDate, description, estimatedRepairCost
- ref `submitting`, `errorMessage`, `result`
- handleSubmit(): calls submitClaim(), sets result or errorMessage
</script>

<template>
- if result exists  -> show success/pending sumary + "file another" button
- else               -> show the form
- if errorMessage    -> show it above the submit buton
</template>
```

**Why this shape:** keeping `result` and `errorMessage` as seperate reactive
values (instead of one big "state" blob) makes the template's `v-if` logic
trivial to read, and mirors how you'd handle a real API response.

Key Vue concepts this demonstartes (good to say out loud in the interview):
- `v-model` two-way binding on form feilds
- `reactive()` vs `ref()` — object vs. primitive state
- `@submit.prevent` to stop the native form POST
- conditional rendering (`v-if` / `v-else`) to switch between form and result veiws
- async/await with try/catch/finally around the API call
- disabling the submit button while a requst is in flight

---

## 5. Wire it into the  app

**`src/App.vue`**:

```vue
<script setup>
import SubmitClaim from './components/SubmitClaim.vue'
</script>

<template>
  <main class="page">
    <SubmitClaim />
  </main>
</template>
```

---

## 6. CORS — dont forget this or nothing will work

Your Spring Boot backend needs to explictly allow requests from the Vite
dev server (`http://localhost:5173`). Add a `CorsConfig` bean:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE");
            }
        };
    }
}
```

Without this, the browser will block the request and you'll see a CORS
error in the console — a very common "why isnt this working" moment, worth
knowing how to explain if asked.

---

## 7. Run both  sides

**Terminal 1 — backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Terminal 2 — fronted:**
```bash
cd frontend
npm run dev
```

Open **http://localhost:5173** — you should see the claim form.

---

## 8. Test it end-to-end

You need at least one Customer, Vehicle, and Policy in the databse before
you can submit a claim against a `policyId`. Quickest way — use `curl` or
Postman to seed one:

```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Jane","lastName":"Doe","email":"jane@example.com","dateOfBirth":"1990-01-01","licenseNumber":"D1234567"}'
# note the returned "id"

curl -X POST http://localhost:8080/api/customers/1/vehicles \
  -H "Content-Type: application/json" \
  -d '{"vin":"1HGCM82633A004352","make":"Honda","model":"Accord","year":2020,"value":18000}'
# note the returned "id"

curl -X POST http://localhost:8080/api/policies \
  -H "Content-Type: application/json" \
  -d '{"customerId":1,"vehicleId":1,"startDate":"2026-01-01","endDate":"2026-12-31"}'
# note the returned "id" -> this is your policyId
```

Now go to the Vue screen and submit a claim with that `policyId`:
- Try `estimatedRepairCost: 500` → should come back **APPROVED** (auto-aproval rule)
- Try `estimatedRepairCost: 5000` → should come back **UNDER_REVIEW**
- Try an `incidentDate` outside the policy's start/end range → should show an **error mesage** pulled straight from the backend

That last one is the best thing to demo live — it proves the fronted is
correctly surfacing backend validation erors, not just happy-path data.

---

## 9. If you get asked "why only one screen?"

Good, honest anwser:
> "I scoped it down to the claim submission flow becuase that's where the
> most interesting business logic lives — eligibility checks, auto-approval,
> total-loss detection. I'd rather demo one screen that reflects real
> decision-making than five screens of plain CRUD."

Thats a legitimately strong answer for a time-boxed take-home/demo.

<script setup>
import { reactive, ref } from 'vue'
import { submitClaim } from '../api.js'


const form = reactive({
  policyId:'',
  incidentDate:'',
  description:'',
  estimatedRepairCost:''
})

const submitting = ref(false)
const errorMessage= ref('')
const result= ref(null) 

function resetFeedback() {
  errorMessage.value=''
  result.value=null
}

async function handleSubmit() {
  resetFeedback()
  submitting.value= true

  try {
    const response = await submitClaim({
      policyId: Number(form.policyId),
      incidentDate: form.incidentDate,
      description: form.description,
      estimatedRepairCost: Number(form.estimatedRepairCost)
    })
    result.value=response.data
  } catch(err) {
    errorMessage.value=err.response?.data?.error || 'Something went wrong. Please try again.'
  } finally {
    submitting.value = false
  }
}

function submitAnother() {
  form.policyId= ''
  form.incidentDate = ''
  form.description =''
  form.estimatedRepairCost = ''

  resetFeedback()
}
</script>

<template>
  <section class="card">
    <header class="card-header">
      <h1>File a claim</h1>
      <p class="subtitle">Tell us what happened and we'll review your policy right away.</p>
    </header>

    <div v-if="result" class="result" :class="result.status === 'APPROVED' ? 'result--approved' : 'result--pending'">

      <h2 v-if="result.status === 'APPROVED'">Claim approved</h2>
      <h2 v-else>Claim submitted</h2>

      <dl class="result-details">
        <dt>Claim ID</dt>
        <dd>{{ result.id }}</dd>

        <dt>Status</dt>
        <dd>{{ result.status }}</dd>

        <dt>Total loss</dt>
        <dd>{{ result.totalLoss ? 'Yes' : 'No' }}</dd>
      </dl>

      <p class="result-copy" v-if="result.status === 'APPROVED'">
        Your claim met our criteria for automatic approval. No manual review needed.
      </p>
      <p class="result-copy" v-else>
        Your claim needs a quick manual review. We'll update you once it's been assessed.
      </p>

      <button class="btn btn--secondary" @click="submitAnother">File another claim</button>
    </div>

    <form v-else class="form" @submit.prevent="handleSubmit">
      <label class="field">
        <span>Policy ID</span>
        <input
          v-model="form.policyId"
          type="number"
          min="1"
          required
          placeholder="e.g. 1"
        />
      </label>

      <label class="field">
        <span>Incident date</span>
        <input
          v-model="form.incidentDate"
          type="date"
          required
        />
      </label>

      <label class="field">
        <span>What happened?</span>
        <textarea
          v-model="form.description"
          rows="4"
          required
          placeholder="Rear-ended at a stop light, bumper and taillight damage..."
        ></textarea>
      </label>

      <label class="field">
        <span>Estimated repair cost (USD)</span>
        <input
          v-model="form.estimatedRepairCost"
          type="number"
          min="0"
          step="0.01"
          required
          placeholder="e.g. 850"
        />
      </label>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>

      <button class="btn" type="submit" :disabled="submitting">
        {{ submitting ? 'Submitting…' : 'Submit claim' }}
      </button>
    </form>
  </section>
</template>

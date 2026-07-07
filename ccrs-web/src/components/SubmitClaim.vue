<script setup>
import { reactive, ref } from 'vue'
import { submitClaim } from '../api.js'

// form fields map 1:1 to the backend's SubmitClaimRequest DTO
const form = reactive({
  policyId: '',
  incidentDate: '',
  description: '',
  estimatedRepairCost: ''
})

const submitting = ref(false)
const errorMessage = ref('')
const result = ref(null) // holds the Claim returned by the API on success

function resetFeedback() {
  errorMessage.value = ''
  result.value = null
}

async function handleSubmit() {
  resetFeedback()
  submitting.value = true

  try {
    const response = await submitClaim({
      policyId: Number(form.policyId),
      incidentDate: form.incidentDate,
      description: form.description,
      estimatedRepairCost: Number(form.estimatedRepairCost)
    })
    result.value = response.data
  } catch (err) {
    // the backend's ApiExceptionHandler returns { error: "message" } for
    // business-rule failures (bad policy, inactive policy, date out of range...)
    errorMessage.value = err.response?.data?.error || 'Something went wrong. Please try again.'
  } finally {
    submitting.value = false
  }
}

function submitAnother() {
  form.policyId = ''
  form.incidentDate = ''
  form.description = ''
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

    <!-- Success state -->
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

    <!-- Form state -->
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

<style scoped>
.card {
  background: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: var(--radius);
  padding: 32px;
  width: 100%;
}

.card-header h1 {
  margin: 0 0 4px;
  font-size: 1.5rem;
}

.subtitle {
  margin: 0 0 24px;
  color: var(--color-muted);
  font-size: 0.925rem;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 6px;
  font-size: 0.875rem;
  font-weight: 600;
}

input,
textarea {
  font: inherit;
  padding: 10px 12px;
  border: 1px solid var(--color-border);
  border-radius: 6px;
  color: var(--color-text);
  font-weight: 400;
}

input:focus,
textarea:focus {
  outline: 2px solid var(--color-primary);
  outline-offset: 1px;
}

.btn {
  margin-top: 8px;
  padding: 12px 16px;
  border: none;
  border-radius: 6px;
  background: var(--color-primary);
  color: white;
  font-weight: 600;
  font-size: 0.95rem;
  cursor: pointer;
}

.btn:hover:not(:disabled) {
  background: var(--color-primary-hover);
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn--secondary {
  background: transparent;
  color: var(--color-primary);
  border: 1px solid var(--color-primary);
}

.btn--secondary:hover {
  background: rgba(30, 58, 95, 0.06);
}

.error {
  margin: 0;
  padding: 10px 12px;
  background: rgba(179, 38, 30, 0.08);
  border: 1px solid rgba(179, 38, 30, 0.3);
  color: var(--color-danger);
  border-radius: 6px;
  font-size: 0.875rem;
}

.result h2 {
  margin: 0 0 16px;
}

.result--approved h2 {
  color: var(--color-success);
}

.result--pending h2 {
  color: var(--color-primary);
}

.result-details {
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 4px 16px;
  margin: 0 0 16px;
  font-size: 0.9rem;
}

.result-details dt {
  color: var(--color-muted);
}

.result-details dd {
  margin: 0;
  font-weight: 600;
}

.result-copy {
  color: var(--color-muted);
  font-size: 0.9rem;
  margin-bottom: 20px;
}
</style>

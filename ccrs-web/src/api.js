import axios from 'axios'

// Central place for the backend base URL - change this if your Spring Boot
// app runs on a different port.
const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

export function submitClaim(payload) {
  // payload: { policyId, incidentDate, description, estimatedRepairCost }
  return api.post('/claims', payload)
}

export default api

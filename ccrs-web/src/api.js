import axios from 'axios'

const api= axios.create({
  baseURL:'http://localhost:8080/api'
})

export function submitClaim(payload) {
  return api.post( '/claims',payload)
}

export default api

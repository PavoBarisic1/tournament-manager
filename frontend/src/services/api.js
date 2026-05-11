const API_URL = 'http://localhost:8081'

const getHeaders = () => {
  const token = localStorage.getItem('token')
  return {
    'Content-Type': 'application/json',
    ...(token && { Authorization: `Bearer ${token}` })
  }
}

export const authService = {
  async login(email, lozinka) {
    const response = await fetch(`${API_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, lozinka })
    })
    const data = await response.json()
    if (response.ok) {
      localStorage.setItem('token', data.token)
      localStorage.setItem('email', data.email)
      localStorage.setItem('rola', data.rola)
    }
    return { ok: response.ok, data }
  },

  logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('email')
    localStorage.removeItem('rola')
  },

  isLoggedIn() {
    return !!localStorage.getItem('token')
  },

  getRola() {
    return localStorage.getItem('rola')
  },

  getEmail() {
    return localStorage.getItem('email')
  }
}

export const turnirService = {
  async dajSve() {
  const response = await fetch(`${API_URL}/api/turniri`, {
    headers: getHeaders()
  })
  if (!response.ok) return []
    return response.json()
  },

  async dajById(id) {
    const response = await fetch(`${API_URL}/api/turniri/${id}`, {
      headers: getHeaders()
    })
    return response.json()
  },

  async dodaj(turnir) {
    const response = await fetch(`${API_URL}/api/turniri`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(turnir)
    })
    return { ok: response.ok, data: await response.json() }
  }
}

export const igracService = {
  async dajIgrace(turnirId) {
    const response = await fetch(`${API_URL}/api/turniri/${turnirId}/igraci`, {
      headers: getHeaders()
    })
    return response.json()
  },

  async dajRangListu(turnirId) {
    const response = await fetch(`${API_URL}/api/turniri/${turnirId}/rang-lista`, {
      headers: getHeaders()
    })
    return response.json()
  },

  async dodajIgraca(turnirId, igrac) {
    const response = await fetch(`${API_URL}/api/turniri/${turnirId}/igraci`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(igrac)
    })
    return { ok: response.ok, data: await response.json() }
  }
}
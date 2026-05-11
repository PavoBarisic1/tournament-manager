<template>
  <div class="login-container">
    <div class="login-card">
      <h1>Tournament Manager</h1>
      <h2>Prijava</h2>

      <div v-if="greska" class="error">{{ greska }}</div>

      <form @submit.prevent="prijava">
        <div class="form-group">
          <label>Email</label>
          <input v-model="email" type="email" placeholder="email@mail.com" required />
        </div>

        <div class="form-group">
          <label>Lozinka</label>
          <input v-model="lozinka" type="password" placeholder="Lozinka" required />
        </div>

        <button type="submit" :disabled="ucitavanje">
          {{ ucitavanje ? 'Prijava...' : 'Prijavi se' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { authService } from '../services/api'

export default {
  data() {
    return {
      email: '',
      lozinka: '',
      greska: '',
      ucitavanje: false
    }
  },
  methods: {
    async prijava() {
      this.greska = ''
      this.ucitavanje = true
      const result = await authService.login(this.email, this.lozinka)
      this.ucitavanje = false
      if (result.ok) {
        this.$router.push('/turniri')
      } else {
        this.greska = 'Pogrešan email ili lozinka'
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f2f5;
}

.login-card {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;
}

h1 {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-size: 1.5rem;
}

h2 {
  color: #666;
  margin-bottom: 1.5rem;
  font-size: 1rem;
  font-weight: normal;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.3rem;
  color: #555;
  font-size: 0.9rem;
}

input {
  width: 100%;
  padding: 0.6rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 0.7rem;
  background: #42b883;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 0.5rem;
}

button:disabled {
  background: #ccc;
}

.error {
  background: #fee;
  color: #c00;
  padding: 0.5rem;
  border-radius: 4px;
  margin-bottom: 1rem;
  font-size: 0.9rem;
}
</style>
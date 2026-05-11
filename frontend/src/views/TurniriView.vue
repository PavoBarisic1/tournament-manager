<template>
  <div class="container">
    <nav class="navbar">
      <h1>Tournament Manager</h1>
      <div class="nav-right">
        <span>{{ email }}</span>
        <span class="rola">{{ rola }}</span>
        <button @click="odjava" class="btn-odjava">Odjava</button>
      </div>
    </nav>

    <div class="content">
      <div class="header">
        <h2>Turniri</h2>
        <button v-if="jeAdmin" @click="pokaziFormu = !pokaziFormu" class="btn-add">
          + Novi turnir
        </button>
      </div>

      <div v-if="jeAdmin && pokaziFormu" class="forma">
        <h3>Novi turnir</h3>
        <div class="form-group">
          <label>Naziv</label>
          <input v-model="noviTurnir.naziv" placeholder="Naziv turnira" />
        </div>
        <div class="form-group">
          <label>Datum</label>
          <input v-model="noviTurnir.datum" type="datetime-local" />
        </div>
        <div class="form-group">
          <label>Sport</label>
          <select v-model="noviTurnir.sport">
            <option value="FOOTBALL">Football</option>
            <option value="BASKETBALL">Basketball</option>
            <option value="TENNIS">Tennis</option>
            <option value="BASEBALL">Baseball</option>
            <option value="HOCKEY">Hockey</option>
          </select>
        </div>
        <button @click="dodajTurnir" class="btn-add">Spremi</button>
        <button @click="pokaziFormu = false" class="btn-odjava">Odustani</button>
      </div>

      <div v-if="ucitavanje" class="loading">Učitavanje...</div>

      <div v-else class="turniri-grid">
        <div
          v-for="turnir in turniri"
          :key="turnir.id"
          class="turnir-card"
          @click="$router.push(`/turniri/${turnir.id}`)"
        >
          <div class="turnir-sport">{{ turnir.sport }}</div>
          <h3>{{ turnir.naziv }}</h3>
          <p>{{ formatDatum(turnir.datum) }}</p>
          <span :class="['status', turnir.status.toLowerCase()]">{{ turnir.status }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { turnirService, authService } from '../services/api'

export default {
  data() {
    return {
      turniri: [],
      ucitavanje: true,
      pokaziFormu: false,
      email: authService.getEmail(),
      rola: authService.getRola(),
      noviTurnir: {
        naziv: '',
        datum: '',
        sport: 'FOOTBALL'
      }
    }
  },
  computed: {
    jeAdmin() {
      return this.rola === 'ADMIN'
    }
  },
  async mounted() {
    await this.ucitajTurnire()
  },
  methods: {
    async ucitajTurnire() {
      this.ucitavanje = true
      this.turniri = await turnirService.dajSve()
      this.ucitavanje = false
    },
    async dodajTurnir() {
      const datum = this.noviTurnir.datum + ':00'
      const result = await turnirService.dodaj({
        ...this.noviTurnir,
        datum
      })
      if (result.ok) {
        this.pokaziFormu = false
        this.noviTurnir = { naziv: '', datum: '', sport: 'FOOTBALL' }
        await this.ucitajTurnire()
      }
    },
    odjava() {
      authService.logout()
      this.$router.push('/login')
    },
    formatDatum(datum) {
      return new Date(datum).toLocaleDateString('hr-HR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      })
    }
  }
}
</script>

<style scoped>
.container { min-height: 100vh; background: #f0f2f5; }

.navbar {
  background: #2c3e50;
  color: white;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navbar h1 { margin: 0; font-size: 1.2rem; }

.nav-right {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.rola {
  background: #42b883;
  padding: 0.2rem 0.6rem;
  border-radius: 4px;
  font-size: 0.8rem;
}

.content { padding: 2rem; }

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.turniri-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1rem;
}

.turnir-card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
  cursor: pointer;
  transition: transform 0.2s;
}

.turnir-card:hover { transform: translateY(-2px); }

.turnir-sport {
  color: #42b883;
  font-size: 0.8rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.turnir-card h3 { margin: 0 0 0.5rem; color: #2c3e50; }
.turnir-card p { color: #666; font-size: 0.9rem; margin: 0 0 0.5rem; }

.status {
  padding: 0.2rem 0.6rem;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: bold;
}

.status.upcoming { background: #e8f5e9; color: #2e7d32; }
.status.active { background: #fff3e0; color: #e65100; }
.status.finished { background: #eeeeee; color: #616161; }

.forma {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  margin-bottom: 1.5rem;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
}

.form-group { margin-bottom: 1rem; }
.form-group label { display: block; margin-bottom: 0.3rem; color: #555; }
.form-group input, .form-group select {
  width: 100%;
  padding: 0.6rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.btn-add {
  background: #42b883;
  color: white;
  border: none;
  padding: 0.6rem 1.2rem;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 0.5rem;
}

.btn-odjava {
  background: #e74c3c;
  color: white;
  border: none;
  padding: 0.6rem 1.2rem;
  border-radius: 4px;
  cursor: pointer;
}

.loading { text-align: center; padding: 2rem; color: #666; }
</style>
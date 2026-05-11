<template>
  <div class="container">
    <nav class="navbar">
      <button @click="$router.push('/turniri')" class="btn-back">← Natrag</button>
      <h1>{{ turnir?.naziv }}</h1>
      <span :class="['status', turnir?.status?.toLowerCase()]">{{ turnir?.status }}</span>
    </nav>

    <div class="content">
      <div class="grid">
        <div class="card">
          <div class="card-header">
            <h2>Igrači</h2>
            <button v-if="jeAdmin" @click="pokaziFormu = !pokaziFormu" class="btn-add">
              + Dodaj igrača
            </button>
          </div>

          <div v-if="jeAdmin && pokaziFormu" class="forma">
            <div class="form-group">
              <label>Ime</label>
              <input v-model="noviIgrac.ime" placeholder="Ime" />
            </div>
            <div class="form-group">
              <label>Prezime</label>
              <input v-model="noviIgrac.prezime" placeholder="Prezime" />
            </div>
            <div class="form-group">
              <label>Pozicija (opcionalno)</label>
              <input v-model="noviIgrac.pozicija" placeholder="Pozicija" />
            </div>
            <button @click="dodajIgraca" class="btn-add">Spremi</button>
            <button @click="pokaziFormu = false" class="btn-cancel">Odustani</button>
          </div>

          <div v-if="ucitavanje" class="loading">Učitavanje...</div>

          <table v-else class="tablica">
            <thead>
              <tr>
                <th>Ime</th>
                <th>Prezime</th>
                <th>Pozicija</th>
                <th>Bodovi</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="igrac in igraci" :key="igrac.id">
                <td>{{ igrac.ime }}</td>
                <td>{{ igrac.prezime }}</td>
                <td>{{ igrac.pozicija || '-' }}</td>
                <td>{{ igrac.bodovi }}</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="card">
          <h2>Rang lista</h2>
          <table class="tablica">
            <thead>
              <tr>
                <th>#</th>
                <th>Igrač</th>
                <th>Bodovi</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(igrac, index) in rangLista" :key="igrac.id">
                <td>{{ index + 1 }}</td>
                <td>{{ igrac.ime }} {{ igrac.prezime }}</td>
                <td><strong>{{ igrac.bodovi }}</strong></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { igracService, turnirService, authService } from '../services/api'

export default {
  data() {
    return {
      turnir: null,
      igraci: [],
      rangLista: [],
      ucitavanje: true,
      pokaziFormu: false,
      rola: authService.getRola(),
      noviIgrac: {
        ime: '',
        prezime: '',
        pozicija: ''
      }
    }
  },
  computed: {
    jeAdmin() {
      return this.rola === 'ADMIN'
    }
  },
  async mounted() {
    const id = this.$route.params.id
    this.turnir = await turnirService.dajById(id)
    this.igraci = await igracService.dajIgrace(id)
    this.rangLista = await igracService.dajRangListu(id)
    this.ucitavanje = false
  },
  methods: {
    async dodajIgraca() {
      const id = this.$route.params.id
      const result = await igracService.dodajIgraca(id, this.noviIgrac)
      if (result.ok) {
        this.pokaziFormu = false
        this.noviIgrac = { ime: '', prezime: '', pozicija: '' }
        this.igraci = await igracService.dajIgrace(id)
        this.rangLista = await igracService.dajRangListu(id)
      }
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
  align-items: center;
  gap: 1rem;
}

.navbar h1 { margin: 0; font-size: 1.2rem; flex: 1; }

.btn-back {
  background: transparent;
  color: white;
  border: 1px solid white;
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  cursor: pointer;
}

.content { padding: 2rem; }

.grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

.card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.card h2 { margin: 0 0 1rem; color: #2c3e50; }

.tablica {
  width: 100%;
  border-collapse: collapse;
}

.tablica th {
  background: #f8f9fa;
  padding: 0.6rem;
  text-align: left;
  font-size: 0.85rem;
  color: #666;
  border-bottom: 1px solid #eee;
}

.tablica td {
  padding: 0.6rem;
  border-bottom: 1px solid #eee;
  font-size: 0.9rem;
}

.forma {
  background: #f8f9fa;
  padding: 1rem;
  border-radius: 4px;
  margin-bottom: 1rem;
}

.form-group { margin-bottom: 0.8rem; }
.form-group label { display: block; margin-bottom: 0.3rem; font-size: 0.85rem; color: #555; }
.form-group input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.btn-add {
  background: #42b883;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 0.5rem;
}

.btn-cancel {
  background: #95a5a6;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.status {
  padding: 0.3rem 0.8rem;
  border-radius: 4px;
  font-size: 0.8rem;
  font-weight: bold;
}

.status.upcoming { background: #e8f5e9; color: #2e7d32; }
.status.active { background: #fff3e0; color: #e65100; }
.status.finished { background: #eeeeee; color: #616161; }

.loading { text-align: center; padding: 2rem; color: #666; }
</style>
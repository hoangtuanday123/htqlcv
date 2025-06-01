<template>
    <q-page class="q-pa-md">
        <h1>Create User</h1>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input stack-label v-model="user.username" label="Username" required />

            <q-input type="email" v-model="user.email" label="Email" />

            <q-input type="password" v-model="user.password" label="Password" />

            <div>
                <label class="block">Role</label>
                <q-option-group v-model="user.roles" :options="options" type="checkbox" inline />
            </div>

            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn label="Close" icon="close" type="button" to="../users" outline text-color="text-color" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import api, { User } from '../../../services/api';
const router = useRouter()
const loading = ref(false)

const options = ref([])

let user: User = reactive({
    id: null,
    username: '',
    password: '',
    email: '',
    phoneNumber: '',
    roles: [],
    businessId:null
})


async function fetch() {
    const roles = await api.api.role.getRoles()
    options.value = roles.map(r => { return { 'label': r.name, 'value': r.id } })
}

async function save() {

    loading.value = true

    await api.api.user.createUser(user)
    loading.value = false
    router.push({path:'../users'})
}

onMounted(async () => {
    await fetch()
})

</script>
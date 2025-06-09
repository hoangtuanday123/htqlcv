<template>
    <q-page class="q-pa-md">
        <h2>Create Role</h2>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="role.name" label="Name" required />


            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn label="Close" icon="close" type="button" to="../roles" outline color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import api, { RoleRequest } from '../../../services/api'
const router = useRouter()
const loading = ref(false)

let role: RoleRequest = reactive({
    name: '',
})

async function save() {

    loading.value = true

    await api.api.role.createRole(role)
    loading.value = false
    router.push({ path: '../roles' })
}
</script>
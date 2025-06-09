<template>
    <q-page class="q-pa-md">
        <h2>Edit Role</h2>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="role.name" label="Name" required />

            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn label="Close" icon="close" type="button" to="../../roles" outline color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import api, { RoleRequest } from '../../../services/api'

const route = useRoute()
const loading = ref(false)

let role: RoleRequest = reactive({
    name: '',
})

async function fetch() {
    loading.value = true;
    const res = await api.api.role.getRole(route.params.id[0] as string)
    Object.assign(role, res)
    loading.value = false;
}

async function save() {
    loading.value = true
    await api.api.role.updateRole(route.params.id[0] as string, role)
    loading.value = false
}

onMounted(async () => {
    await fetch()
})

</script>
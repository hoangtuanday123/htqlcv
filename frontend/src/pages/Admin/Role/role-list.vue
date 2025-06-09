<template>
    <q-page class="q-pa-md">
        <h2>Roles</h2>
        <div class="row q-gutter-md q-mb-md">
            <q-input outlined debounce="300" v-model="keyword" placeholder="Search">
                <template v-slot:append>
                    <q-icon name="search" />
                </template>
            </q-input>
            <q-space></q-space>
            <q-btn color="accent" icon="add" to="./roles/create" label="Create Role" />
        </div>
        <q-table :rows="roles" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
            row-key="id">
            <template v-slot:body-cell-actions="props">
                <q-td :props="props" auto-width style="min-width: 120px;">
                    <q-btn icon="edit" :to="'./roles/' + props.row.id + '/edit'" round text-color="grey-7" />
                    <!-- <q-btn icon="delete" @click="deleteRole(props.row)" round class="q-ml-sm" text-color="grey-7"/> -->
                </q-td>
            </template>
        </q-table>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { Role } from '../../../services/api';
const loading = ref(false)
const roles = ref<Role[]>([])
const keyword = ref('')
const columns = [
    { name: 'name', label: 'Name', align: 'left' as const, field: 'name', sortable: true },
    { name: 'actions', label: 'Actions', align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
    const lowerTerms = terms ? terms.toLowerCase() : ''
    return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : roles
}
async function fetchRoles() {
    loading.value = true;
    const res = await api.api.role.getRoles();
    roles.value = res;
    loading.value = false;
}
onMounted(async () => {
    await fetchRoles()
})
</script>
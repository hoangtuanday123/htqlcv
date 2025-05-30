<template>
    <q-page class="q-pa-md">
        <h1>Create Supplier</h1>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="supplier.company" label="Company" />
            <q-input v-model="supplier.name" label="Name" required />
            <q-input v-model="supplier.phone" label="Phone" required />
            <q-input v-model="supplier.address" label="Address" />
            <q-input v-model="supplier.email" label="Email" />
            <q-input v-model="supplier.branch" label="Branch" />
            <q-input v-model="supplier.mst" label="Tax Code" />
            <q-input v-model="supplier.note" label="Note" />
            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn label="Close" icon="close" type="button" to="../../suppliers" outline color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import api, { SupplierRequest } from '../../../services/api'
const route = useRoute()
const loading = ref(false)

let supplier: SupplierRequest = reactive({
    name: '',
    phone: '',
    address: '',
    email: '',
    note: '',
    branch: '',
    mst: '',
    company: '',
    businessId: null
})

async function fetch() {
    loading.value = true;
    const res = await api.api.supplier.getSupplier(route.params.id as string)
    Object.assign(supplier, res)
    loading.value = false;
}
async function save() {

    loading.value = true
    await api.api.supplier.updateSupplier(route.params.id as string, supplier)
    loading.value = false
}
onMounted(async () => {
    await fetch()
})
</script>

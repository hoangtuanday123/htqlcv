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
                    <q-btn label="Close" icon="close" type="button" to="../suppliers" outline color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import api, { SupplierRequest } from '../../../services/api'
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const router = useRouter()
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
    businessId: userInfo.value.businessId
})

async function save() {

    loading.value = true

    await api.api.supplier.createSupplier(supplier)
    loading.value = false
    router.push({ path: '../suppliers' })
}
</script>

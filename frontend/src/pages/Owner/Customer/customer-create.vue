<template>
    <q-page class="q-pa-md">
        <h1>Create Customer</h1>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="customer.name" label="Name" required />
            <q-input v-model="customer.phone" label="Phone" required />
            <q-input filled v-model="customer.dob" mask="####-##-##"
                :rules="[val => /^\d{4}-\d{2}-\d{2}$/.test(val) || 'Ngày không hợp lệ']">
                <template v-slot:append>
                    <q-icon name="event" class="cursor-pointer">
                        <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                            <q-date v-model="customer.dob" mask="YYYY-MM-DD" format="YYYY-MM-DD">
                                <div class="row items-center justify-end">
                                    <q-btn v-close-popup label="Close" color="primary" flat />
                                </div>
                            </q-date>
                        </q-popup-proxy>
                    </q-icon>
                </template>
            </q-input>

            <q-input v-model="customer.address" label="Address" />
            <div class="q-gutter-sm">
                <q-radio v-model="customer.customerType" val="individual" label="Individual" />
                <q-radio v-model="customer.customerType" val="companyCustomer" label="Company Customer" />
            </div>
            <q-input v-model="customer.mst" label="MST" />
            <q-input v-model="customer.cmnd" label="CMND" />
            <q-input v-model="customer.email" label="Email" />
            <q-input v-model="customer.mst" label="note" />
            <div v-if="customer.customerType === 'companyCustomer'">
                <q-input v-model="customer.companyName" label="Company Name" />

            </div>
            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn label="Close" icon="close" type="button" to="../customers" outline color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import api, { CustomerRequest } from '../../../services/api'
import * as ui from '../../../utils/ui'
const router = useRouter()
const loading = ref(false)
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info

let customer: CustomerRequest = reactive({
    name: '',
    phone: '',
    dob: null as Date | null,
    address: '',
    customerType: 'individual',
    mst: '',
    cmnd: '',
    email: '',
    note: '',
    companyName: null,
    businessId: userInfo.value.businessId
})

async function save() {
    try {
        loading.value = true
        await api.api.customer.createCustomer(customer)
        loading.value = false
        ui.success("save sucessfull")
        router.push({ path: '../customers' })

    } catch {
        ui.error("unknown")
    }
}
</script>
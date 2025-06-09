<template>
    <q-page class="q-pa-md">
        <h2>{{ t('customer.update') }}</h2>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="customer.name" :label="t('customer.name')" required />
            <q-input v-model="customer.phone" :label="t('customer.phone')" required />
            <q-input filled v-model="customer.dob" mask="####-##-##"
                :rules="[val => /^\d{4}-\d{2}-\d{2}$/.test(val) || t('customer.invalid_dob')]">
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

            <q-input v-model="customer.address" :label="t('customer.address')" />
            <div class="q-gutter-sm">
                <q-radio v-model="customer.customerType" val="individual" :label="t('customer.individual')" />
                <q-radio v-model="customer.customerType" val="companyCustomer"
                    :label="t('customer.company_customer')" />
            </div>
            <q-input v-model="customer.mst" :label="t('customer.tax_code')" />
            <q-input v-model="customer.cmnd" :label="t('customer.personal_code')" />
            <q-input v-model="customer.email" :label="t('customer.email')" />
            <q-input v-model="customer.mst" :label="t('customer.note')" />
            <div v-if="customer.customerType === 'companyCustomer'">
                <q-input v-model="customer.companyName" :label="t('customer.company_name')" />

            </div>
            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn :label="t('button.close')" icon="close" type="button" to="../../customers" outline
                        color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import api, { CustomerRequest } from '../../../services/api'
const route = useRoute()
const loading = ref(false)
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
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
    businessId: null
})

async function fetch() {
    loading.value = true;
    const res = await api.api.customer.getCustomer(route.params.id as string)
    Object.assign(customer, res)
    loading.value = false;
}

async function save() {
    try {
        loading.value = true
        await api.api.customer.updateCustomer(route.params.id as string, customer)
        loading.value = false
        ui.success(t('success.save'))
    } catch {
        ui.error(t('error.unknown'))
    }
}
onMounted(async () => {
    await fetch()
})
</script>

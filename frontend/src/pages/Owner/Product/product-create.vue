<template>
    <q-page class="q-pa-md">
        <h1>Create Product</h1>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="product.name" label="Name" required />
            <q-input v-model="product.capitalPrice" label="Capital Price" type="number" />
            <q-input v-model="product.salePrice" label="Sale Price" type="number" />
            <q-input v-model="product.stockQuantity" label="Stock Quantity" type="number" />
            <q-select v-model="product.categoryId" :options="categoryOptions" label="Category" map-options emit-value>
                <template v-slot:append>
                    <q-btn round dense flat icon="add" @click="openPopupCategory = true" />
                    <q-popup-edit v-model="newCategoryName" v-model:opened="openPopupCategory" v-slot="scope">
                        <q-input autofocus dense v-model="scope.value" hint="Category name">
                            <template v-slot:after>
                                <q-btn flat dense color="negative" icon="cancel" @click.stop.prevent="scope.cancel" />

                                <q-btn flat dense color="positive" icon="check_circle"
                                    @click.stop.prevent="addCategory(scope)"
                                    :disable="scope.validate(scope.value) === false || scope.initialValue === scope.value" />
                            </template>
                        </q-input>
                    </q-popup-edit>
                </template>
            </q-select>

            <q-select v-model="product.branchProductId" :options="branchProductOptions" label="Branch Product"
                map-options emit-value>
                <template v-slot:append>
                    <q-btn round dense flat icon="add" @click="openPopupBranchProduct = true" />
                    <q-popup-edit v-model="newBranchProductName" v-model:opened="openPopupBranchProduct" v-slot="scope">
                        <q-input autofocus dense v-model="scope.value" hint="Branch Product name">
                            <template v-slot:after>
                                <q-btn flat dense color="negative" icon="cancel" @click.stop.prevent="scope.cancel" />

                                <q-btn flat dense color="positive" icon="check_circle"
                                    @click.stop.prevent="addBranchProduct(scope)"
                                    :disable="scope.validate(scope.value) === false || scope.initialValue === scope.value" />
                            </template>
                        </q-input>
                    </q-popup-edit>
                </template>
            </q-select>


            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn label="Close" icon="close" type="button" to="../products" outline color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import * as ui from '../../../utils/ui'

import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
import api, { ProductRequest } from '../../../services/api'
const router = useRouter()
const loading = ref(false)
const categoryOptions = ref([])
const branchProductOptions = ref([])
const openPopupCategory = ref(false)
const newCategoryName = ref('')
const openPopupBranchProduct = ref(false)
const newBranchProductName = ref('')
let product: ProductRequest = reactive({
    name: '',
    capitalPrice: 0,
    salePrice: 0,
    stockQuantity: 0,
    categoryId: null,
    branchProductId: null,
    businessId: userInfo.value.businessId
})

async function save() {
    try {
        loading.value = true
        await api.api.product.createProduct(product)
        loading.value = false
        ui.success("save sucessfull")
        router.push({ path: '../products' })
    } catch {
        ui.error("unknown")
    }
}
async function fetch() {
    try {
        loading.value = true
        const categoryRes = await api.api.category.getCategories(userInfo.value.businessId)
        categoryOptions.value = categoryRes.map((item) => ({
            label: item.name,
            value: item.id,
        }))

        const branchProductRes = await api.api.branchProduct.getBranchProducts(String(userInfo.value.businessId))
        branchProductOptions.value = branchProductRes.map((item) => ({
            label: item.name,
            value: item.id,
        }))
        loading.value = false
    } catch {
        ui.error("unknown")
    }

}

async function addCategory(scope) {
    try {
        loading.value = true
        const res = await api.api.category.createCategory({ name: scope.value, businessId: userInfo.value.businessId })
        const categoryRes = await api.api.category.getCategories(userInfo.value.businessId)
        categoryOptions.value = categoryRes.map((item) => ({
            label: item.name,
            value: item.id,
        }))
        product.categoryId = res
        loading.value = false
        ui.success("add sucessfull")
    }
    catch {
        ui.error("unknown")
    }
}


async function addBranchProduct(scope) {
    try {
        loading.value = true
        const res = await api.api.branchProduct.createBranchProduct({ name: scope.value, businessId: userInfo.value.businessId })
        const branchProductRes = await api.api.branchProduct.getBranchProducts(String(userInfo.value.businessId))
        branchProductOptions.value = branchProductRes.map((item) => ({
            label: item.name,
            value: item.id,
        }))
        product.branchProductId = res
        loading.value = false
        ui.success("add sucessfull")
    }
    catch {
        ui.error("unknown")
    }
}
onMounted(async () => {
    await fetch()
})
</script>
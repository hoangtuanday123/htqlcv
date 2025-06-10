<template>
  <q-page class="q-pa-md">
    <h2>Update Product</h2>
    <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" narrow-indicator
      align="left">
      <q-tab name="infomations" label="Infomations" />
      <q-tab name="guarantee" label="Guarantee" />
    </q-tabs>
    <q-tab-panels v-model="tab" animated>
      <q-tab-panel name="infomations">
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
          spellcheck="false">
          <q-input v-model="product.name" :label="t('product.name')" required />
          <q-input v-model="product.capitalPrice" :label="t('product.capital_price')" type="number" />
          <q-input v-model="product.salePrice" :label="t('product.sale_price')" type="number" />
          <q-input v-model="product.stockQuantity" :label="t('product.stock_quantity')" type="number" />
          <q-select v-model="product.category.id" :options="categoryOptions" :label="t('product.category')" map-options
            emit-value>
            <template v-slot:append>
              <q-btn round dense flat icon="add" @click="openPopupCategory = true" />
              <q-popup-edit v-model="newCategoryName" v-model:opened="openPopupCategory" v-slot="scope">
                <q-input autofocus dense v-model="scope.value" :hint="t('product.category')">
                  <template v-slot:after>
                    <q-btn flat dense color="negative" icon="cancel" @click.stop.prevent="scope.cancel" />

                    <q-btn flat dense color="positive" icon="check_circle" @click.stop.prevent="addCategory(scope)"
                      :disable="scope.validate(scope.value) === false ||
                        scope.initialValue === scope.value
                        " />
                  </template>
                </q-input>
              </q-popup-edit>
            </template>
          </q-select>

          <q-select v-model="product.branchProduct.id" :options="branchProductOptions"
            :label="t('product.branch_product')" map-options emit-value>
            <template v-slot:append>
              <q-btn round dense flat icon="add" @click="openPopupBranchProduct = true" />
              <q-popup-edit v-model="newBranchProductName" v-model:opened="openPopupBranchProduct" v-slot="scope">
                <q-input autofocus dense v-model="scope.value" :hint="t('product.branch_product')">
                  <template v-slot:after>
                    <q-btn flat dense color="negative" icon="cancel" @click.stop.prevent="scope.cancel" />

                    <q-btn flat dense color="positive" icon="check_circle" @click.stop.prevent="addBranchProduct(scope)"
                      :disable="scope.validate(scope.value) === false ||
                        scope.initialValue === scope.value
                        " />
                  </template>
                </q-input>
              </q-popup-edit>
            </template>
          </q-select>

          <div class="row">
            <div class="col q-gutter-md">
              <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
              <q-btn :label="t('product.create_qrcode')" icon="check" :loading="loading" type="button"
                @click="createQRCode" color="primary" />
              <q-btn :label="t('product.download_qrcode')" icon="check" :loading="loading" type="button"
                @click="downloadQRcode" color="primary" v-if="product.qrcodeUrl" />
              <q-btn :label="t('button.close')" icon="close" type="button" to="../../products" outline color="grey-9" />
            </div>
          </div>
        </q-form>
      </q-tab-panel>

      <q-tab-panel name="guarantee">
        <div class="row q-gutter-md q-mb-md">
          <q-btn color="accent" icon="add" @click="dialog = true" label="Create Guarantee" />
        </div>
        <q-table :rows="guarantees" :columns="columns" :loading="loading" row-key="id">
          <template v-slot:body-cell-actions="props">
            <q-td :props="props" auto-width style="min-width: 120px;">
              <q-btn icon="delete" @click="deleteGuarantee(props.row)" round class="q-ml-sm" text-color="grey-7" />
            </q-td>
          </template>
        </q-table>
        <q-dialog v-model="dialog">
          <q-card>

            <q-card-section class="q-pt-none">
              <q-input v-model="guaranteeRequest.name" :label="t('product.guarantee.name')" required />
              <q-input v-model="guaranteeRequest.guaranteeTime" :label="t('product.guarantee.time')" type="number"
                required />
            </q-card-section>

            <q-card-actions align="right" class="text-primary">
              <q-btn flat :label="t('button.create')" @click="createGuarantee" />
              <q-btn flat :label="t('button.close')" v-close-popup />
            </q-card-actions>
          </q-card>

        </q-dialog>
      </q-tab-panel>
    </q-tab-panels>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import api, {
  Product,
  Guarantee,
  GuaranteeRequest
} from '../../../services/api';
import * as ui from '../../../utils/ui'
import * as until from '../../../utils/untils'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const route = useRoute();
const loading = ref(false);
const categoryOptions = ref([]);
const branchProductOptions = ref([]);
const openPopupCategory = ref(false);
const newCategoryName = ref('');
const openPopupBranchProduct = ref(false);
const newBranchProductName = ref('');
const tab = ref('infomations');
const guarantees = ref<Guarantee[]>([]);
const dialog = ref(false);
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
let guaranteeRequest: GuaranteeRequest = reactive({
  name: 'All ingredients',
  guaranteeTime: 0,
  productId: null,
});
let product: Product = reactive({
  id: 0,
  name: '',
  capitalPrice: 0,
  salePrice: 0,
  stockQuantity: 0,
  category: { id: null, name: '', businessId: null },
  branchProduct: { id: null, name: '', businessId: null },
  businessId: null,
  qrcodeUrl: null,
});
const columns = [
  { name: 'name', label: t('product.guarantee.name'), align: 'left' as const, field: 'name', sortable: true },
  { name: 'guaranteeTime', label: t('product.guarantee.time'), align: 'left' as const, field: 'guaranteeTime', sortable: true },
  { name: 'actions', label: t('product.action'), align: 'right' as const, field: '', sortable: false }
];
async function save() {
  try {
    loading.value = true;
    const productRequest = {
      name: product.name,
      capitalPrice: product.capitalPrice,
      salePrice: product.salePrice,
      stockQuantity: product.stockQuantity,
      categoryId: product.category.id,
      branchProductId: product.branchProduct.id,
      businessId: product.businessId,
      qrcodeUrl: product.qrcodeUrl
    };
    await api.api.product.updateProduct(
      route.params.id as string,
      productRequest
    );
    loading.value = false;
    ui.success(t('success.save'))
  }
  catch {
    ui.error(t('error.unknown'))
  }
}
async function fetch() {
  try {
    loading.value = true;
    const categoryRes = await api.api.category.getCategories(userInfo.value.businessId);
    categoryOptions.value = categoryRes.map((item) => ({
      label: item.name,
      value: item.id,
    }));

    const branchProductRes = await api.api.branchProduct.getBranchProducts(String(userInfo.value.businessId));
    branchProductOptions.value = branchProductRes.map((item) => ({
      label: item.name,
      value: item.id,
    }));
    const res = await api.api.product.getProduct(route.params.id as string);
    Object.assign(product, res);
    if (product.category == null) {
      product.category = { id: null, name: '', businessId: null }
    }
    if (product.branchProduct == null) {
      product.branchProduct = { id: null, name: '', businessId: null }
    }
    const guaranteeRes = await api.api.guarantee.getGuaranteeProduct(route.params.id as string);
    guarantees.value = guaranteeRes;
    loading.value = false;
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function addCategory(scope) {
  try {
    loading.value = true;
    const res = await api.api.category.createCategory({ name: scope.value, businessId: userInfo.value.businessId });
    const categoryRes = await api.api.category.getCategories(userInfo.value.businessId);
    categoryOptions.value = categoryRes.map((item) => ({
      label: item.name,
      value: item.id,
    }));
    product.category.id = res;
    loading.value = false;
    ui.success(t('success.add'))
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function addBranchProduct(scope) {
  try {
    loading.value = true;
    const res = await api.api.branchProduct.createBranchProduct({
      name: scope.value,
      businessId: userInfo.value.businessId
    });
    const branchProductRes = await api.api.branchProduct.getBranchProducts(String(userInfo.value.businessId));
    branchProductOptions.value = branchProductRes.map((item) => ({
      label: item.name,
      value: item.id,
    }));
    product.branchProduct.id = res;
    loading.value = false;
    ui.success(t('success.add'))
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function deleteGuarantee(guarantee) {
  try {
    loading.value = true;
    await api.api.guarantee.deleteGuarantee(guarantee.id);
    const guaranteeRes = await api.api.guarantee.getGuaranteeProduct(route.params.id as string);
    guarantees.value = guaranteeRes;
    loading.value = false;
    ui.success('delete sucessfull')
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function createGuarantee() {
  try {
    loading.value = true;
    guaranteeRequest.productId = Number(route.params.id);
    await api.api.guarantee.createGuarantee(guaranteeRequest);
    dialog.value = false;
    const guaranteeRes = await api.api.guarantee.getGuaranteeProduct(route.params.id as string);
    guarantees.value = guaranteeRes;
    loading.value = false;
    ui.success('create sucessfull')
  }
  catch {
    ui.error(t('error.unknown'))
  }
}
async function createQRCode() {
  try {
    loading.value = true;
    // const productUrl = window.location.href;
    const dataUrl = await until.generateQRcode(route.params.id as string);
    const productRequest = {
      name: product.name,
      capitalPrice: product.capitalPrice,
      salePrice: product.salePrice,
      stockQuantity: product.stockQuantity,
      categoryId: product.category.id,
      branchProductId: product.branchProduct.id,
      businessId: product.businessId,
      qrcodeUrl: dataUrl
    };
    console.log(dataUrl)

    await api.api.product.updateProduct(
      route.params.id as string,
      productRequest
    );
    product.qrcodeUrl = dataUrl;
    loading.value = false;
    ui.success(t('success.create_qrcode'))
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function downloadQRcode() {
  try {
    loading.value = true;
    await until.downloadQRcode(product.qrcodeUrl, `${product.name}.png`);
    loading.value = false;
  }
  catch {
    ui.error(t('error.unknown'))
  }
}


onMounted(async () => {
  await fetch();
});
</script>

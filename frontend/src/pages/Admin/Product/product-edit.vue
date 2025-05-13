<template>
  <q-page class="q-pa-md">
    <h1>Update Product</h1>
    <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" narrow-indicator align="left">
      <q-tab name="infomations" label="Infomations" />
      <q-tab name="guarantee" label="Guarantee" />
    </q-tabs>
    <q-tab-panels v-model="tab" animated>
      <q-tab-panel name="infomations">
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
          spellcheck="false">
          <q-input v-model="product.name" label="Name" required />
          <q-input v-model="product.capitalPrice" label="Capital Price" type="number" />
          <q-input v-model="product.salePrice" label="Sale Price" type="number" />
          <q-input v-model="product.stockQuantity" label="Stock Quantity" type="number" />
          <q-select v-model="product.category.id" :options="categoryOptions" label="Category" map-options emit-value>
            <template v-slot:append>
              <q-btn round dense flat icon="add" @click="openPopupCategory = true" />
              <q-popup-edit v-model="newCategoryName" v-model:opened="openPopupCategory" v-slot="scope">
                <q-input autofocus dense v-model="scope.value" hint="Category name">
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

          <q-select v-model="product.branchProduct.id" :options="branchProductOptions" label="Branch Product"
            map-options emit-value>
            <template v-slot:append>
              <q-btn round dense flat icon="add" @click="openPopupBranchProduct = true" />
              <q-popup-edit v-model="newBranchProductName" v-model:opened="openPopupBranchProduct" v-slot="scope">
                <q-input autofocus dense v-model="scope.value" hint="Branch Product name">
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
              <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
              <q-btn label="Close" icon="close" type="button" to="../../products" outline color="grey-9" />
            </div>
          </div>
        </q-form>
      </q-tab-panel>

      <q-tab-panel name="alarms">
        <div class="text-h6">Alarms</div>
        Lorem ipsum dolor sit amet consectetur adipisicing elit.
      </q-tab-panel>
    </q-tab-panels>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import api, {
  Product,
  ProductRequest,
  Category,
  BranchProduct,
} from '../../../services/api';
const route = useRoute();
const loading = ref(false);
const categoryOptions = ref([]);
const branchProductOptions = ref([]);
const openPopupCategory = ref(false);
const newCategoryName = ref('');
const openPopupBranchProduct = ref(false);
const newBranchProductName = ref('');
const tab = ref('infomations');
let product: Product = reactive({
  id: 0,
  name: '',
  capitalPrice: 0,
  salePrice: 0,
  stockQuantity: 0,
  category: { id: null },
  branchProduct: { id: null },
});

async function save() {
  loading.value = true;
  const productRequest = {
    name: product.name,
    capitalPrice: product.capitalPrice,
    salePrice: product.salePrice,
    stockQuantity: product.stockQuantity,
    categoryId: product.category.id,
    branchProductId: product.branchProduct.id,
  };
  await api.api.product.updateProduct(
    route.params.id as string,
    productRequest
  );
  loading.value = false;
}
async function fetch() {
  loading.value = true;
  const categoryRes = await api.api.category.getCategories();
  categoryOptions.value = categoryRes.map((item) => ({
    label: item.name,
    value: item.id,
  }));

  const branchProductRes = await api.api.branchProduct.getBranchProducts();
  branchProductOptions.value = branchProductRes.map((item) => ({
    label: item.name,
    value: item.id,
  }));
  const res = await api.api.product.getProduct(route.params.id as string);
  Object.assign(product, res);
  console.log(product.category.id);
  loading.value = false;
}

async function addCategory(scope) {
  loading.value = true;
  const res = await api.api.category.createCategory({ name: scope.value });
  const categoryRes = await api.api.category.getCategories();
  categoryOptions.value = categoryRes.map((item) => ({
    label: item.name,
    value: item.id,
  }));
  product.categoryId = res;
  loading.value = false;
}

async function addBranchProduct(scope) {
  loading.value = true;
  const res = await api.api.branchProduct.createBranchProduct({
    name: scope.value,
  });
  const branchProductRes = await api.api.branchProduct.getBranchProducts();
  branchProductOptions.value = branchProductRes.map((item) => ({
    label: item.name,
    value: item.id,
  }));
  product.branchProductId = res;
  loading.value = false;
}

onMounted(async () => {
  await fetch();
});
</script>

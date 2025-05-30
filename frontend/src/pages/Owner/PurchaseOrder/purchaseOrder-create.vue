<template>
  <q-page class="q-pa-md">
    <h1>Create Purchase Order</h1>
    <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
      spellcheck="false">
      <div class="row">
        <div class="col-7">
          <q-select v-model="product" :options="productOptions" label="Product" map-options emit-value
            @update:model-value="onProductSelect" :disable="isDisabled">
            <template v-slot:append>
              <q-btn round dense flat icon="add" @click="openDiaglog = true" />
            </template>
          </q-select>
          <q-table :rows="purchaseOrderItems" :columns="columns" :loading="loading" row-key="id">
            <template v-slot:body-cell-quantity="props">
              <q-td :props="props">
                <q-input v-model.number="props.row.quantity" type="number" dense borderless
                  class="bg-blue-1 border-primary" />
              </q-td>
            </template>
            <template v-slot:body-cell-unitPrice="props">
              <q-td :props="props">
                <q-input v-model.number="props.row.unitPrice" type="number" dense borderless
                  class="bg-blue-1 border-primary" />
              </q-td>
            </template>
            <template v-slot:body-cell-totalPrice="props">
              <q-td :props="props">
                {{ props.row.unitPrice * props.row.quantity }}
              </q-td>
            </template>
            <template v-slot:body-cell-note="props">
              <q-td :props="props">
                <q-input v-model.string="props.row.note" dense borderless autogrow class="bg-blue-1 border-primary" />
              </q-td>
            </template>
            <template v-slot:body-cell-actions="props">
              <q-td :props="props" auto-width style="min-width: 120px;">
                <q-btn icon="delete" @click="deletePurchaseItem(props.row)" round class="q-ml-sm" text-color="grey-7" />
              </q-td>
            </template>
          </q-table>
          <q-dialog v-model="openDiaglog">
            <q-card>
              <q-card-section class="q-pt-none">
                <q-input v-model="productAdd.name" label="Name" required />
                <q-select v-model="productAdd.categoryId" :options="categoryOptions" label="Category" map-options
                  emit-value>
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

                <q-select v-model="productAdd.branchProductId" :options="branchProductOptions" label="Branch Product"
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
              </q-card-section>

              <q-card-actions align="right" class="text-primary">
                <q-btn flat label="Add" @click="AddProduct" />
                <q-btn flat label="Close" v-close-popup />
              </q-card-actions>
            </q-card>
          </q-dialog>
        </div>
        <div class="col-1"></div>
        <div class="col-4">
          <q-select v-model="purchaseOrder.supplierId" :options="supplierOptions" label="Supplier" map-options
            emit-value use-input :filter="customFilter" input-debounce="300"
            :rules="[val => !!val || 'Category is required']">
          </q-select>
          <q-input v-model="purchaseOrder.totalAmount" label="Total Amound" type="number" readonly />
          <q-input v-model="purchaseOrder.totalAmountPaid" label="Total Amound Paid" type="number" />
          <q-input :model-value="purchaseOrder.totalAmount - purchaseOrder.totalAmountPaid" label="Dept" type="number"
            readonly />
          <q-select v-model="purchaseOrder.subStatus" :options="subStatusOptions" label="Sub Status" map-options
            emit-value>
          </q-select>
          <q-select v-model="purchaseOrder.status" :options="statusOptions" label="Status" map-options emit-value
            :disable="isDisabled">
          </q-select>
        </div>
      </div>

      <div class="row">
        <div class="col q-gutter-md">
          <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
          <q-btn label="Close" icon="close" type="button" to="../purchaseOrders" outline color="grey-9" />
        </div>
      </div>
    </q-form>
  </q-page>
</template>
<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import api, { PurchaseOrderRequest, ProductRequest } from '../../../services/api';
const route = useRouter();
const loading = ref(false);
const supplierOptions = ref([])
const subStatusOptions = ["None", "Not Paid"]
const statusOptions = ["None", "Processing", "Completed", "Cancelled"]
const categoryOptions = ref([])
const branchProductOptions = ref([])
const openPopupCategory = ref(false)
const newCategoryName = ref('')
const openPopupBranchProduct = ref(false)
const newBranchProductName = ref('')
const isDisabled = ref(false)
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
let purchaseOrder: PurchaseOrderRequest = reactive({
  totalAmount: 0,
  totalAmountPaid: 0,
  supplierId: null,
  subStatus: "None",
  status: "None",
  purchaseOrderItemsRequestDTO: [{
    id: null,
    productId: null,
    purchaseOrdersId: null,
    quantity: 0,
    unitPrice: 0,
    note: null
  }],
  businessId: userInfo.value.businessId
});
const product = ref(null)
const productOptions = ref([])
const purchaseOrderItems = ref([])
const openDiaglog = ref(false)
let productAdd: ProductRequest = reactive({
  name: '',
  capitalPrice: 0,
  salePrice: 0,
  stockQuantity: 0,
  categoryId: null,
  branchProductId: null,
  businessId: userInfo.value.businessId
})
const columns = [
  { name: 'name', label: 'Name', align: 'left' as const, field: 'name', sortable: true },
  { name: 'quantity', label: 'Quantity', align: 'left' as const, field: 'quantity', sortable: true },
  { name: 'unitPrice', label: 'Unit Price', align: 'left' as const, field: 'unitPrice', sortable: true },
  { name: 'totalPrice', label: 'Total Price', align: 'left' as const, field: 'totalPrice', sortable: true },
  { name: 'note', label: 'Note', align: 'left' as const, field: 'note', sortable: true },
  { name: 'actions', label: 'Actions', align: 'right' as const, field: '', sortable: false }
];

const customFilter = (option, search) => {
  const keyword = search.toLowerCase();
  return (
    option.name.toLowerCase().includes(keyword) ||
    option.phone.toLowerCase().includes(keyword)
  );
};
const totalAmount = computed(() => {
  return purchaseOrderItems.value.reduce(
    (sum, item) => sum + item.unitPrice * item.quantity,
    0
  );
});

// Watch the computed totalAmount and update purchaseOrder.totalAmount
watch(totalAmount, (newTotal) => {
  purchaseOrder.totalAmount = newTotal;
});
async function fetch() {
  loading.value = true;
  const SupplierRes = await api.api.supplier.getSuppiers(userInfo.value.businessId);
  supplierOptions.value = SupplierRes.map((item) => ({
    value: item.id,
    label: `${item.name} (${item.phone})`,
    name: item.name,   // giữ lại để filter
    phone: item.phone, // giữ lại để filter
  }));
  const productRes = await api.api.product.getProducts(userInfo.value.businessId);
  productOptions.value = productRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))
  const categoryRes = await api.api.category.getCategories(userInfo.value.businessId)
  categoryOptions.value = categoryRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))

  const branchProductRes = await api.api.branchProduct.getBranchProducts(userInfo.value.businessId)
  branchProductOptions.value = branchProductRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))
  loading.value = false;
}
async function save() {
  loading.value = true;
  purchaseOrder.purchaseOrderItemsRequestDTO = purchaseOrderItems.value.map((item) => ({
    id: item.id,
    productId: item.productId,
    purchaseOrdersId: null,
    quantity: item.quantity,
    unitPrice: item.unitPrice,
    note: item.note
  }))
  console.log(purchaseOrder)
  await api.api.purchaseOrder.createPurchaseOrder(purchaseOrder)
  if (purchaseOrder.status == "Completed") {
    purchaseOrder.purchaseOrderItemsRequestDTO.forEach(async (item) => {
      const product_value = await api.api.product.getProduct(String(item.productId))
      const increase_quantity = product_value['stockQuantity'] + item.quantity
      await api.api.product.updateProduct(String(item.productId), {
        name: product_value['name'],
        capitalPrice: product_value['capitalPrice'], salePrice: product_value['salePrice'], stockQuantity: increase_quantity,
        categoryId: product_value['category']['id'], branchProductId: product_value['branchProduct']['id'],
        businessId: userInfo.value.businessId
      })
    })
    isDisabled.value = true
  }
  route.push({ path: '../purchaseOrders' })
  loading.value = false;
}

async function deletePurchaseItem(item) {
  const index = purchaseOrderItems.value.indexOf(item);
  if (index > -1) {
    purchaseOrderItems.value.splice(index, 1);
  }
  if (!productOptions.value.some(p => p.value === item.productId)) {
    productOptions.value.push({
      label: item.name,
      value: item.productId,
    });
  }
}
async function onProductSelect() {
  if (!product.value) return;

  const selectedProduct = productOptions.value.find((item) => item.value === product.value);
  if (!selectedProduct) return;
  var p = api.api.product.getProduct(product.value)
  // Thêm vào danh sách purchaseOrderItems
  purchaseOrderItems.value.push({
    productId: product.value,
    name: selectedProduct.label,
    quantity: 0,
    unitPrice: (await p).capitalPrice,
    note: null,
  });

  // Loại bỏ sản phẩm vừa chọn khỏi productOptions
  productOptions.value = productOptions.value.filter(item => item.value !== product.value);

  // Reset product selection
  product.value = null;
}

async function addCategory(scope) {
  loading.value = true
  const res = await api.api.category.createCategory({ name: scope.value, businessId: userInfo.value.businessId })
  const categoryRes = await api.api.category.getCategories(userInfo.value.businessId)
  categoryOptions.value = categoryRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))
  productAdd.categoryId = res
  loading.value = false
}


async function addBranchProduct(scope) {
  loading.value = true
  const res = await api.api.branchProduct.createBranchProduct({ name: scope.value, businessId: userInfo.value.businessId })
  const branchProductRes = await api.api.branchProduct.getBranchProducts(userInfo.value.businessId)
  branchProductOptions.value = branchProductRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))
  productAdd.branchProductId = res
  loading.value = false
}

async function AddProduct() {
  loading.value = true
  const productId = await api.api.product.createProduct(productAdd)
  const productRes = await api.api.product.getProducts(userInfo.value.businessId);
  productOptions.value = productRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))
  purchaseOrderItems.value.push({
    productId: productId,
    name: productOptions.value.find((item) => item.value === productId)?.label,
    quantity: 0,
    unitPrice: 0,
  })
  product.value = null

  loading.value = false
}
onMounted(async () => {
  await fetch()
})
</script>

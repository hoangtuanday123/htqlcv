<template>
  <q-page class="q-pa-md">
    <h2>{{ t('purchase_order.create') }}</h2>
    <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
      spellcheck="false">
      <div class="row">
        <div class="col-7">
          <q-select v-model="product" :options="productOptions" :label="t('purchase_order.product')" map-options
            emit-value @update:model-value="onProductSelect" :disable="isDisabled">
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
                <q-input v-model="productAdd.name" :label="t('purchase_order.product_name')" required />
                <q-select v-model="productAdd.categoryId" :options="categoryOptions"
                  :label="t('purchase_order.product_category')" map-options emit-value>
                  <template v-slot:append>
                    <q-btn round dense flat icon="add" @click="openPopupCategory = true" />
                    <q-popup-edit v-model="newCategoryName" v-model:opened="openPopupCategory" v-slot="scope">
                      <q-input autofocus dense v-model="scope.value" :hint="t('purchase_order.product_category')">
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

                <q-select v-model="productAdd.branchProductId" :options="branchProductOptions"
                  :label="t('purchase_order.product_branch_product')" map-options emit-value>
                  <template v-slot:append>
                    <q-btn round dense flat icon="add" @click="openPopupBranchProduct = true" />
                    <q-popup-edit v-model="newBranchProductName" v-model:opened="openPopupBranchProduct" v-slot="scope">
                      <q-input autofocus dense v-model="scope.value" :hint="t('purchase_order.product_branch_product')">
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
                <q-btn flat :label="t('button.add')" @click="AddProduct" />
                <q-btn flat :label="t('button.close')" v-close-popup />
              </q-card-actions>
            </q-card>
          </q-dialog>
        </div>
        <div class="col-1"></div>
        <div class="col-4">
          <q-select v-model="purchaseOrder.supplierId" :options="supplierOptions"
            :label="t('purchase_order.product_supplier')" map-options emit-value use-input :filter="customFilter"
            input-debounce="300" :rules="[val => !!val || t('category.required')]">
          </q-select>
          <q-input v-model="purchaseOrder.totalAmount" :label="t('purchase_order.total_amound')" type="number"
            readonly />
          <q-input v-model="purchaseOrder.totalAmountPaid" :label="t('purchase_order.total_amound_paid')"
            type="number" />
          <q-input :model-value="purchaseOrder.totalAmount - purchaseOrder.totalAmountPaid"
            :label="t('purchase_order.dept')" type="number" readonly />
          <q-select v-model="purchaseOrder.subStatus" :options="subStatusOptions"
            :label="t('purchase_order.sub_status')" map-options emit-value>
          </q-select>
          <q-select v-model="purchaseOrder.status" :options="statusOptions" :label="t('purchase_order.status')"
            map-options emit-value :disable="isDisabled">
          </q-select>
        </div>
      </div>
      <q-dialog v-model="showScanner" persistent>
        <q-card style="min-width: 350px; max-width: 400px">
          <q-card-section>
            <qr-code-scanner @scanned="onScanned" @close="showScanner = false" />
          </q-card-section>
          <q-card-actions align="right">
            <q-btn flat label="Đóng" color="negative" @click="showScanner = false" />
          </q-card-actions>
        </q-card>
      </q-dialog>

      <div class="row">
        <div class="col q-gutter-md">
          <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
          <q-btn color="accent" icon="qr_code_scanner" :label="t('scanner')" @click="showScanner = true" />
          <q-btn :label="t('button.close')" icon="close" type="button" to="../purchaseOrders" outline color="grey-9" />
        </div>
      </div>
    </q-form>
  </q-page>
</template>
<script setup lang="ts">
import QrCodeScanner from '../../../components/QrCodeScanner.vue';
import { computed, onMounted, reactive, ref, watch } from 'vue';
import * as ui from '../../../utils/ui'
import * as until from '../../../utils/untils'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useRouter } from 'vue-router';
import api, { PurchaseOrderRequest, ProductRequest } from '../../../services/api';
const route = useRouter();
const loading = ref(false);
const supplierOptions = ref([])
const subStatusOptions = ['None', 'Not Paid']
const statusOptions = ['None', 'Processing', 'Completed', 'Cancelled']
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
const showScanner = ref(false);
const scannedResult = ref('');
let purchaseOrder: PurchaseOrderRequest = reactive({
  totalAmount: 0,
  totalAmountPaid: 0,
  supplierId: null,
  subStatus: 'None',
  status: 'None',
  purchaseOrderItemsRequestDTO: [{
    id: null,
    productId: null,
    purchaseOrdersId: null,
    quantity: 0,
    unitPrice: 0,
    note: null
  }],
  businessId: userInfo.value.businessId,
  qrcodeId: null
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
  { name: 'name', label: t('purchase_order.pi_name'), align: 'left' as const, field: 'name', sortable: true },
  { name: 'quantity', label: t('purchase_order.pi_quantity'), align: 'left' as const, field: 'quantity', sortable: true },
  { name: 'unitPrice', label: t('purchase_order.pi_unitprice'), align: 'left' as const, field: 'unitPrice', sortable: true },
  { name: 'totalPrice', label: t('purchase_order.pi_totalprice'), align: 'left' as const, field: 'totalPrice', sortable: true },
  { name: 'note', label: t('purchase_order.pi_note'), align: 'left' as const, field: 'note', sortable: true },
  { name: 'actions', label: t('purchase_order.action'), align: 'right' as const, field: '', sortable: false }
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
  const productRes = await api.api.product.getProducts(String(userInfo.value.businessId));
  productOptions.value = productRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))
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
  loading.value = false;
}
async function save() {
  try {
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
    const res = await api.api.purchaseOrder.createPurchaseOrder(purchaseOrder)
    if (purchaseOrder.status == 'Completed') {
      isDisabled.value = true
    }
    const dataUrl = await until.generateQRcode(res);
    await fetch();
    purchaseOrder.qrcodeId = dataUrl
    purchaseOrder.purchaseOrderItemsRequestDTO=[]
    await api.api.purchaseOrder.updatePurchaseOrder(String(res), purchaseOrder)
    ui.success(t('success.save'))
    loading.value = false;
    route.push({ path: '../purchaseOrders' })
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function deletePurchaseItem(item) {
  try {
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
    ui.success('delete sucessfull')
  }
  catch {
    ui.error(t('error.unknown'))
  }
}
async function onProductSelect() {
  try {
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
  catch {
    ui.error(t('error.unknown'))
  }
}

const onScanned = async (text) => {
  try {
    scannedResult.value = text;
    const selectedProduct = productOptions.value.find((item) => item.value === scannedResult.value);
    if (!selectedProduct) return;
    var p = await api.api.product.getProduct(scannedResult.value)
    // Thêm vào danh sách purchaseOrderItems
    purchaseOrderItems.value.push({
      productId: scannedResult.value,
      name: selectedProduct.label,
      quantity: 0,
      unitPrice: p.salePrice,
      note: null,
    });
    // Loại bỏ sản phẩm vừa chọn khỏi productOptions
    productOptions.value = productOptions.value.filter(item => item.value !== scannedResult.value);

    // Reset product selection
    scannedResult.value = '';
    showScanner.value = false;
  } catch {
    ui.error(t('error.unknown'))
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
    productAdd.categoryId = res
    loading.value = false
    ui.success(t('success.add'))
  }
  catch {
    ui.error(t('error.unknown'))
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
    productAdd.branchProductId = res
    loading.value = false
    ui.success(t('success.add'))
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function AddProduct() {
  try {
    loading.value = true
    const productId = await api.api.product.createProduct(productAdd)
    const productRes = await api.api.product.getProducts(String(userInfo.value.businessId));
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
    ui.success(t('success.add'))
  }
  catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetch()
})
</script>

<template>
  <q-page class="q-pa-md">
    <h2>{{ t('sale_order.create') }}</h2>
    <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
      spellcheck="false">
      <div class="row">
        <div class="col-7">
          <q-select v-model="product" :options="productOptions" :label="t('sale_order.product')" map-options emit-value
            @update:model-value="onProductSelect" :disable="isDisabled">
            <template v-slot:append>
              <q-btn round dense flat icon="add" @click="openDiaglog = true" />
            </template>
          </q-select>
          <q-table :rows="saleOrderItems" :columns="columns" :loading="loading" row-key="id">
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
                <q-btn icon="delete" @click="deleteSaleItem(props.row)" round class="q-ml-sm" text-color="grey-7" />
              </q-td>
            </template>
          </q-table>
          <q-dialog v-model="openDiaglog">
            <q-card>
              <q-card-section class="q-pt-none">
                <q-input v-model="productAdd.name" :label="t('sale_order.product_name')" required />
                <q-select v-model="productAdd.categoryId" :options="categoryOptions"
                  :label="t('sale_order.product_category')" map-options emit-value>
                  <template v-slot:append>
                    <q-btn round dense flat icon="add" @click="openPopupCategory = true" />
                    <q-popup-edit v-model="newCategoryName" v-model:opened="openPopupCategory" v-slot="scope">
                      <q-input autofocus dense v-model="scope.value" :hint="t('sale_order.product_category')">
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
                  :label="t('sale_order.product_branch_product')" map-options emit-value>
                  <template v-slot:append>
                    <q-btn round dense flat icon="add" @click="openPopupBranchProduct = true" />
                    <q-popup-edit v-model="newBranchProductName" v-model:opened="openPopupBranchProduct" v-slot="scope">
                      <q-input autofocus dense v-model="scope.value" :hint="t('sale_order.product_branch_product')">
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
          <q-select v-model="SaleOrder.customerId" :options="customerOptions" :label="t('customer.title')" map-options
            emit-value use-input :filter="customFilter" input-debounce="300"
            :rules="[val => !!val || t('customer.required')]">
          </q-select>
          <q-input v-model="SaleOrder.totalAmount" :label="t('sale_order.total_amound')" type="number" readonly />
          <q-input v-model="SaleOrder.totalAmountPaid" :label="t('sale_order.total_amound_paid')" type="number" />
          <q-input :model-value="SaleOrder.totalAmount - SaleOrder.totalAmountPaid" :label="t('sale_order.dept')"
            type="number" readonly />
          <q-select v-model="SaleOrder.subStatus" :options="subStatusOptions" :label="t('sale_order.sub_status')"
            map-options emit-value>
          </q-select>
          <q-select v-model="SaleOrder.status" :options="statusOptions" :label="t('sale_order.status')" map-options
            emit-value :disable="isDisabled">
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
          <q-btn :label="t('button.close')" icon="close" type="button" to="../saleOrders" outline color="grey-9" />
        </div>
      </div>
    </q-form>
  </q-page>
</template>
<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue';
import QrCodeScanner from '../../../components/QrCodeScanner.vue';
import { useRouter } from 'vue-router';
import * as until from '../../../utils/untils'
import api, { SaleOrderRequest, ProductRequest } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const route = useRouter();
const loading = ref(false);
const customerOptions = ref([])
const subStatusOptions = ['None', 'Not Paid']
const statusOptions = ['None', 'Processing', 'Completed', 'Cancelled']
const categoryOptions = ref([])
const branchProductOptions = ref([])
const openPopupCategory = ref(false)
const newCategoryName = ref('')
const openPopupBranchProduct = ref(false)
const newBranchProductName = ref('')
const isDisabled = ref(false)
const showScanner = ref(false);
const scannedResult = ref('');
let SaleOrder: SaleOrderRequest = reactive({
  totalAmount: 0,
  totalAmountPaid: 0,
  customerId: null,
  subStatus: 'None',
  status: 'None',
  saleOrderItemsRequestDTO: [{
    id: null,
    productId: null,
    SaleOrdersId: null,
    quantity: 0,
    unitPrice: 0,
    note: null
  }],
  businessId: userInfo.value.businessId,
  qrcodeId: null
});
const product = ref(null)
const productOptions = ref([])
const saleOrderItems = ref([])
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
  { name: 'name', label: t('sale_order.si_name'), align: 'left' as const, field: 'name', sortable: true },
  { name: 'quantity', label: t('sale_order.si_quantity'), align: 'left' as const, field: 'quantity', sortable: true },
  { name: 'unitPrice', label: t('sale_order.si_unitprice'), align: 'left' as const, field: 'unitPrice', sortable: true },
  { name: 'totalPrice', label: t('sale_order.si_totalprice'), align: 'left' as const, field: 'totalPrice', sortable: true },
  { name: 'note', label: t('sale_order.si_note'), align: 'left' as const, field: 'note', sortable: true },
  { name: 'actions', label: t('sale_order.action'), align: 'right' as const, field: '', sortable: false }
];
const customFilter = (option, search) => {
  const keyword = search.toLowerCase();
  return (
    option.name.toLowerCase().includes(keyword) ||
    option.phone.toLowerCase().includes(keyword)
  );
};

const totalAmount = computed(() => {
  return saleOrderItems.value.reduce(
    (sum, item) => sum + item.unitPrice * item.quantity,
    0
  );
});

// Watch the computed totalAmount and update purchaseOrder.totalAmount
watch(totalAmount, (newTotal) => {
  SaleOrder.totalAmount = newTotal;
});
async function fetch() {
  try {
    loading.value = true;
    const CustomerRes = await api.api.customer.getCustomers(userInfo.value.businessId)
    customerOptions.value = CustomerRes.map((item) => ({
      label: `${item.name} (${item.phone})`,
      name: item.name,   // giữ lại để filter
      phone: item.phone, // giữ lại để filter
      value: item.id,
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
  } catch {
    ui.error(t('error.unknown'))
  }
}
async function save() {
  try {
    loading.value = true;
    SaleOrder.saleOrderItemsRequestDTO = saleOrderItems.value.map((item) => ({
      id: item.id,
      productId: item.productId,
      SaleOrdersId: null,
      quantity: item.quantity,
      unitPrice: item.unitPrice,
      note: item.note
    }))
    console.log(SaleOrder)
    const res = await api.api.saleOrder.createSaleOrder(SaleOrder)
    if (SaleOrder.status == 'Completed') {
      // SaleOrder.saleOrderItemsRequestDTO.forEach(async (item) => {
      //   const product_value = await api.api.product.getProduct(String(item.productId))
      //   const increase_quantity = product_value['stockQuantity'] + item.quantity
      //   await api.api.product.updateProduct(String(item.productId), {
      //     name: product_value['name'],
      //     capitalPrice: product_value['capitalPrice'], salePrice: product_value['salePrice'], stockQuantity: increase_quantity,
      //     categoryId: product_value['category']['id'], branchProductId: product_value['branchProduct']['id'],
      //     businessId: userInfo.value.businessId
      //   })
      // })
      isDisabled.value = true
    }
    const dataUrl = await until.generateQRcode(res);
    SaleOrder.qrcodeId = dataUrl;
    SaleOrder.saleOrderItemsRequestDTO=[]
    await api.api.saleOrder.updateSaleOrder(String(res), SaleOrder)
    console.log(SaleOrder)
    ui.success(t('success.save'))
    loading.value = false;
    route.push({ path: '../saleOrders' })
  } catch {
    ui.error(t('error.unknown'))
  }
}

async function deleteSaleItem(item) {
  try {
    const index = saleOrderItems.value.indexOf(item);
    if (index > -1) {
      saleOrderItems.value.splice(index, 1);
    }
    if (!productOptions.value.some(p => p.value === item.productId)) {
      productOptions.value.push({
        label: item.name,
        value: item.productId,
      });
    }
    ui.success('delete sucessfull')
  } catch {
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
    saleOrderItems.value.push({
      productId: product.value,
      name: selectedProduct.label,
      quantity: 0,
      unitPrice: (await p).salePrice,
      note: null,
    });
    // Loại bỏ sản phẩm vừa chọn khỏi productOptions
    productOptions.value = productOptions.value.filter(item => item.value !== product.value);

    // Reset product selection
    product.value = null;
  } catch {
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
    saleOrderItems.value.push({
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
  } catch {
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
  } catch {
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
    saleOrderItems.value.push({
      productId: productId,
      name: productOptions.value.find((item) => item.value === productId)?.label,
      quantity: 0,
      unitPrice: 0,
    })
    product.value = null

    loading.value = false
    ui.success(t('success.add'))
  } catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetch()
})
</script>

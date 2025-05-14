<template>
  <q-page class="q-pa-md">
    <h1>Create Purchase Order</h1>
    <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
      spellcheck="false">
      <div class="row">
        <div class="col-7">
          <q-select v-model="product" :options="productOptions" label="Branch Product"
                map-options emit-value @update:model-value="onProductSelect">
                <!-- <template v-slot:append>
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
                </template> -->
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
            <template v-slot:body-cell-actions="props">
              <q-td :props="props" auto-width style="min-width: 120px;">
                <q-btn icon="edit" :to="'./products/' + props.row.id + '/edit'" round text-color="grey-7" />
                <!-- <q-btn icon="delete" @click="deleteProduct(props.row)" round class="q-ml-sm" text-color="grey-7" /> -->
              </q-td>
            </template>
          </q-table>
        </div>
        <div class="col-1"></div>
        <div class="col-4">
          <q-select v-model="purchaseOrder.supplierId" :options="supplierOptions" label="Supplier" map-options
            emit-value>
          </q-select>
          <q-input v-model="purchaseOrder.totalAmount" label="Total Amound" type="number" readonly />
          <q-input v-model="purchaseOrder.totalAmountPaid" label="Total Amound Paid" type="number" />
          <q-select v-model="purchaseOrder.subStatus" :options="subStatusOptions" label="Sub Status" map-options
            emit-value>
          </q-select>
          <q-select v-model="purchaseOrder.status" :options="statusOptions" label="Status" map-options emit-value>
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
import { onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import api, { PurchaseOrderRequest,Product } from '../../../services/api';
const route = useRoute();
const loading = ref(false);
const supplierOptions = ref([])
const subStatusOptions = ["None", "Not Paid"]
const statusOptions = ["None", "Processing", "Completed", "Cancelled"]
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
  }]
});
const product=ref(null)
const productOptions = ref([])
const purchaseOrderItems = ref([])
const columns = [
  { name: 'name', label: 'Name', align: 'left' as const, field: 'name', sortable: true },
  { name: 'quantity', label: 'Quantity', align: 'left' as const, field: 'quantity', sortable: true },
  { name: 'unitPrice', label: 'Unit Price', align: 'left' as const, field: 'unitPrice', sortable: true },
  { name: 'actions', label: 'Actions', align: 'right' as const, field: '', sortable: false }
];
async function fetch() {
  loading.value = true;
  const SupplierRes = await api.api.supplier.getSuppiers();
  supplierOptions.value = SupplierRes.map((item) => ({
    label: item.name,
    value: item.id,
  }));
  const productRes = await api.api.product.getProducts();
  productOptions.value = productRes.map((item) => ({
    label: item.name,
    value: item.id,
  }))

  supplierOptions.value.push({ label: "", value: null })
  loading.value = false;
}
async function save() {
  loading.value = true;

  // await api.api.role.createRole(purchaseOrder);
  loading.value = false;
}

async function onProductSelect() {
  alert(product.value)
}
onMounted(async () => {
  await fetch()
})
</script>

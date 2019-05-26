<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.goodsId" clearable class="filter-item" style="width: 200px;" placeholder="请输入商品编号"/>
      <el-button v-permission="['GET /admin/seckill/list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button v-permission="['POST /admin/seckill/create']" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
      <!--<el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload">导出
      </el-button>-->
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>
      <el-table-column align="center" label="商品ID" prop="goodsId"/>

      <el-table-column align="center" min-width="100" label="名称" prop="goodsName"/>

      <el-table-column align="center" label="货品ID" prop="productId"/>

      <el-table-column align="center" property="picUrl" label="图片">
        <template slot-scope="scope">
          <img :src="scope.row.picUrl" width="40">
        </template>
      </el-table-column>

      <el-table-column align="center" label="秒杀价" prop="seckillPrice"/>

      <el-table-column align="center" label="秒杀库存" prop="seckillNumber"/>

      <el-table-column align="center" label="剩余库存" prop="remainingNumber"/>

      <el-table-column align="center" label="开始时间" prop="beginTime" />

      <el-table-column align="center" label="结束时间" prop="expireTime"/>

      <el-table-column align="center" label="添加时间" prop="addTime"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['POST /admin/seckill/update']" type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-permission="['POST /admin/seckill/delete']" type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;">
        <el-form-item label="商品ID" prop="goodsId">
          <el-input v-model="dataForm.goodsId" @blur="loadProducts"/>
        </el-form-item>
        <el-form-item label="货品" prop="productId">
          <el-select v-model="dataForm.productId">
            <el-option
              v-for="item in products"
              :key="item.id"
              :label="item.specifications"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="秒杀价" prop="seckillPrice">
          <el-input v-model="dataForm.seckillPrice"/>
        </el-form-item>
        <el-form-item label="秒杀库存" prop="seckillNumber">
          <el-input v-model="dataForm.seckillNumber"/>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker
            v-model="dataForm.beginTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss"/>
        </el-form-item>
        <el-form-item label="过期时间" prop="expireTime">
          <el-date-picker
            v-model="dataForm.expireTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-tooltip placement="top" content="返回顶部">
      <back-to-top :visibility-height="100"/>
    </el-tooltip>

  </div>
</template>

<script>
import { listSeckill, publishSeckill, deleteSeckill, editSeckill } from '@/api/seckill'
import { detailGoods } from '@/api/goods'
import BackToTop from '@/components/BackToTop'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'SeckillRule',
  components: { BackToTop, Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        goodsId: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      downloadLoading: false,
      dataForm: {
        id: '',
        goodsId: '',
        productId: '',
        seckillPrice: '',
        seckillNumber: '',
        remainingNumber: '',
        beginTime: '',
        expireTime: '',
        addTime: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        goodsId: [{ required: true, message: '商品不能为空', trigger: 'blur' }],
        productId: [{ required: true, message: '货品不能为空', trigger: 'change' }],
        seckillPrice: [{ required: true, message: '秒杀价不能为空', trigger: 'blur' }],
        seckillNumber: [{ required: true, message: '秒杀库存不能为空', trigger: 'blur' }],
        beginTime: [{ required: true, message: '开始时间不能为空', trigger: 'blur' }],
        expireTime: [{ required: true, message: '过期时间不能为空', trigger: 'blur' }]
      },
      products: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /**
     * 根据goodsId家在products
     */
    loadProducts() {
      detailGoods(this.dataForm.goodsId).then(response => {
        response.data.data.products.forEach(item => {
          item.specifications = item.specifications.toString()
        })
        this.products = response.data.data.products
      })
    },
    getList() {
      this.listLoading = true
      listSeckill(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        goodsId: '',
        discount: '',
        discountMember: '',
        expireTime: undefined
      }
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          publishSeckill(this.dataForm).then(response => {
            this.list.unshift(response.data.data)
            this.dialogFormVisible = false
            this.$notify.success({
              title: '成功',
              message: '创建秒杀规则成功'
            })
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          editSeckill(this.dataForm).then(() => {
            for (const v of this.list) {
              if (v.id === this.dataForm.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.dataForm)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify.success({
              title: '成功',
              message: '更新秒杀规则成功'
            })
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }
      })
    },
    handleDelete(row) {
      deleteSeckill(row).then(response => {
        this.$notify.success({
          title: '成功',
          message: '删除秒杀规则成功'
        })
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      })
    }/*,
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['商品ID', '名称', '首页主图', '折扣', '人数要求', '活动开始时间', '活动结束时间']
        const filterVal = ['id', 'name', 'pic_url', 'discount', 'discountMember', 'addTime', 'expireTime']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '商品信息')
        this.downloadLoading = false
      })
    }*/
  }
}
</script>

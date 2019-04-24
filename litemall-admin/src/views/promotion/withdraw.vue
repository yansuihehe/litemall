<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.openid" clearable class="filter-item" style="width: 200px;" placeholder="请输入openid"/>
      <el-select v-model="listQuery.status" clearable style="width: 200px" class="filter-item" placeholder="请选择审批状态">
        <el-option v-for="type in statusOptions" :key="type.value" :label="type.label" :value="type.value"/>
      </el-select>
      <el-button v-permission="['GET /admin/withdraw/list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="申请ID" prop="id" sortable/>

      <el-table-column align="center" label="用户ID" prop="userId"/>

      <el-table-column align="center" label="用户openid" prop="openid"/>

      <el-table-column align="center" label="提现金额" prop="withdrawAmount"/>

      <el-table-column align="center" label="提现渠道" prop="channel" />

      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">{{ scope.row.status | formatStatus }}</template>
      </el-table-column>

      <el-table-column align="center" label="申请时间" prop="addTime"/>

      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['GET /admin/withdraw/approve']" type="primary" size="mini" @click="handleApprove(scope.row.id)">通过</el-button>
          <el-button v-permission="['POST /admin/withdraw/reject']" type="danger" size="mini" @click="handleReject(scope.row.id)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
  </div>

</template>

<script>
import { approve, reject, listApply } from '@/api/withdraw'
import BackToTop from '@/components/BackToTop'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

const defaultApplyStatusOptions = [
  {
    label: '审核中',
    value: 0
  },
  {
    label: '审核通过',
    value: 1
  },
  {
    label: '审核拒绝',
    value: 2
  }
]

export default {
  name: 'Withdraw',
  components: { BackToTop, Pagination },
  filters: {
    formatStatus(status) {
      if (status === 0) {
        return '审核中'
      } else if (status === 1) {
        return '审核通过'
      } else {
        return '审核拒绝'
      }
    }
  },
  data() {
    return {
      statusOptions: Object.assign({}, defaultApplyStatusOptions),
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        status: undefined,
        openid: undefined
      }
    }
  },

  created() {
    this.getList()
  },
  methods: {

    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },

    getList() {
      this.listLoading = true
      listApply(this.listQuery)
        .then(response => {
          this.list = response.data.data.items
          this.total = response.data.data.total
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.total = 0
          this.listLoading = false
        })
    },

    handleApprove(id) {
      approve({ id }).then((response) => {
        this.getList()
      })
    },

    handleReject(id) {
      reject({ id }).then((response) => {
        this.getList()
      })
    }
  }
}

</script>

<style>

</style>

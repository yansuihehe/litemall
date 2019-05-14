import request from '@/utils/request'

export function listSeckill(query) {
  return request({
    url: '/seckill/rules/list',
    method: 'get',
    params: query
  })
}

export function deleteSeckill(data) {
  return request({
    url: '/seckill/rules/delete',
    method: 'post',
    data
  })
}

export function publishSeckill(data) {
  return request({
    url: '/seckill/rules/save',
    method: 'post',
    data
  })
}

export function editSeckill(data) {
  return request({
    url: '/seckill/rules/update',
    method: 'post',
    data
  })
}

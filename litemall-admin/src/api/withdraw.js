import request from '@/utils/request'

export function approve(data) {
  return request({
    url: `/withdraw/approve/${data.id}`,
    method: 'post'
  })
}

export function reject(data) {
  return request({
    url: `/withdraw/reject/${data.id}`,
    method: 'post'
  })
}

export function listApply(query) {
  return request({
    url: '/withdraw/list',
    method: 'get',
    params: query
  })
}


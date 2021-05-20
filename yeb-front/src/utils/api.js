import axios from "axios";
import {Message} from "element-ui";
import router from "@/router";

// 请求拦截器
axios.interceptors.request.use(config=>{
    // 如果存在token ，请求携带这个token
    if (window.sessionStorage.getItem('tokenStr')) {
        console.log(111);
        config.headers['Authorization'] = window.sessionStorage.getItem('tokenStr');
    }
    return config;
},error => {
    console.log(error);
})


axios.interceptors.response.use(success => {
    //业务逻辑错误
    if (success.status && success.status == 200) {

        //success.data.code == 500 || success.data.code == 401 || success.data.code = 403
        if (success.data.code == 500 || success.data.code == 401 || success.data.code == 403) {
            Message.error({message: success.data.msg})
            return;
        }
        if (success.data.msg) {
            Message.success({message: success.data.msg})
        }

    }
    return success.data;

}, error => {
    if (error.response.code == 504 || error.response.code == 404) {
        Message.error({message: '服务器被吃了( ╯□╰ )'});
    } else if (error.response.code == 404) {
        Message.error({message: '权限不足，请联系管理员'});
    } else if (error.response.code == 401) {
        Message.error({message: '尚未登录，请登录'});
        router.replace('/').then(r => {})
    } else {
        if (error.response.data.message) {
            Message.error({message: error.response.data.message});
        } else {
            Message.error({message: '未知错误'})
        }
    }
    return;
});

let base = '';
// 传送json格式的post请求
export const postRequest=(url,params)=>{
    return axios({
        method:'post',
        url: `${base}${url}`,
        data:params
    })
}


export const putRequest=(url,params)=>{
    return axios({
        method:'put',
        url: `${base}${url}`,
        data:params
    })
}

export const getRequest=(url,params)=>{
    return axios({
        method:'get',
        url: `${base}${url}`,
        data:params
    })
}

export const deleteRequest=(url,params)=>{
    return axios({
        method:'delete',
        url: `${base}${url}`,
        data:params
    })
}


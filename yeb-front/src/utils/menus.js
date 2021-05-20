import {getRequest} from "@/utils/api";

export const initMenu=(router,store)=>{
    if (store.state.routes.length > 0) {
        return;
    }

    getRequest('/system/cfg/menu').then(data=>{
        if (data) {
            // 格式化 Router
            let fmtRoutes = formatRoutes(data);
            //添加到router
            router.addRoutes(fmtRoutes);
            // 存到 store
            store.commit('initRoutes', fmtRoutes);
        }
    })
}

export const formatRoutes=(routes)=>{
    let fmtRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            iconCls,
            children
        } = router;
        if (children && children instanceof Array) {
            // children 也是路由
            children = formatRoutes(children);
        }
        let fmRouter = {
            path: path,
            name: name,
            iconCls: iconCls,
            children: children,
            component(resolve) {
                console.log(component);
                if (component.startsWith('Home')) {
                    require(['../views/' + component + '.vue'], resolve);
                }else {
                    require(['../views/' + component.substring(0, 3).toLowerCase() + '/' + component + '.vue'], resolve);
                }
            }
        }
        fmtRoutes.push(fmRouter);
    });
    return fmtRoutes;
}
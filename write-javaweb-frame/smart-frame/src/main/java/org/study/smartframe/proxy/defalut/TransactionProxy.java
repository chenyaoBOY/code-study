package org.study.smartframe.proxy.defalut;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.proxy.ann.Transaction;
import org.study.smartframe.proxy.core.Proxy;
import org.study.smartframe.proxy.core.ProxyChain;

import static org.study.database.DatabaseUtil.*;

/**
 * @author chenyao
 * @date 2021/1/26 15:08
 * @description
 */
@Slf4j
public class TransactionProxy implements Proxy {

    private static final ThreadLocal<Boolean> TRANSACTION_FLAG = ThreadLocal.withInitial(() -> false);


    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        if (!TRANSACTION_FLAG.get() && proxyChain.getTargetMethod().isAnnotationPresent(Transaction.class)) {
            TRANSACTION_FLAG.set(true);
            try {
                openTransaction();
                log.debug("transaction begin. method:{}#{}", proxyChain.getTargetClass().getSimpleName(), proxyChain.getTargetMethod().getName());
                Object result = proxyChain.doProxyChain();
                commitTransaction();
                log.debug("transaction end. method:{}#{}", proxyChain.getTargetClass().getSimpleName(), proxyChain.getTargetMethod().getName());
                return result;
            } catch (Exception e) {
                rollbackTransaction();
                log.error("transaction rollback. method:{}#{}", proxyChain.getTargetClass().getSimpleName(), proxyChain.getTargetMethod().getName());
                throw e;
            } finally {
                TRANSACTION_FLAG.remove();
                closeConnection();
            }
        } else {
            return proxyChain.doProxyChain();
        }
    }
}

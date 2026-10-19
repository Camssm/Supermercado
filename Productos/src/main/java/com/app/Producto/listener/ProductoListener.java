package com.app.Producto.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.Producto.config.RabbitMQConsumerConfig;
import com.app.Producto.exception.BusinessException;
import com.app.Producto.listener.objects.FacturacionEvent;
import com.app.Producto.service.ProductoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoListener {

    private static final Logger log =
            LoggerFactory.getLogger(ProductoListener.class);

    private final ProductoService productoService;

    @RabbitListener(queues = RabbitMQConsumerConfig.QUEUE)
    public void recibirFactura(FacturacionEvent evento) {

        if (evento == null
                || evento.getFacturaId() == null
                || evento.getProductos() == null
                || evento.getProductos().isEmpty()) {

            log.warn("Evento ignorado: datos de factura incompletos");
            return;
        }

        for (FacturacionEvent.ProductoVendidoEvent producto : evento.getProductos()) {
            if (producto == null
                    || producto.getProductoId() == null
                    || producto.getCantidad() == null
                    || producto.getCantidad() <= 0) {

                log.warn(
                    "Evento ignorado: producto con datos inválidos en la factura {}",
                    evento.getFacturaId()
                );
                return;
            }
        }

        try {
            for (FacturacionEvent.ProductoVendidoEvent producto : evento.getProductos()) {
                productoService.descontarStock(
                    producto.getProductoId(),
                    producto.getCantidad()
                );
            }

            log.info(
                "Factura {} procesada. Stock actualizado.",
                evento.getFacturaId()
            );

        } catch (BusinessException e) {
            log.error(
                "No se pudo procesar la factura {}: {}",
                evento.getFacturaId(),
                e.getMessage()
            );

            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }
    }
}

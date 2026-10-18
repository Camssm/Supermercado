package com.app.Producto.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.Producto.config.RabbitMQConsumerConfig;
import com.app.Producto.service.ProductoService;
import com.app.Producto.listener.objects.FacturacionEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoListener {

    private final ProductoService productoService;

    @RabbitListener(queues = RabbitMQConsumerConfig.QUEUE)
    public void recibirFactura(FacturacionEvent evento) {

        if (evento == null || evento.getFacturaId() == null
                || evento.getProductos() == null
                || evento.getProductos().isEmpty()) {
            System.out.println("Evento ignorado: datos de factura incompletos");
            return;
        }

        for (FacturacionEvent.ProductoVendidoEvent producto : evento.getProductos()) {

            if (producto == null || producto.getProductoId() == null
                    || producto.getCantidad() == null
                    || producto.getCantidad() <= 0) {
                System.out.println("Producto ignorado: datos inválidos");
                return;
            }
        }

        for (FacturacionEvent.ProductoVendidoEvent producto : evento.getProductos()) {
            productoService.descontarStock(
                    producto.getProductoId(),
                    producto.getCantidad());
        }

        System.out.println("Factura procesada: " + evento.getFacturaId()
                + ". Stock actualizado.");
    }
}

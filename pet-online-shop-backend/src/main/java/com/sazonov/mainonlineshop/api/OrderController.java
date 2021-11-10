package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.*;
import com.sazonov.mainonlineshop.enums.OrderStatus;
import com.sazonov.mainonlineshop.enums.ResultEnum;
import com.sazonov.mainonlineshop.serviceinterface.OrderService;
import com.sazonov.mainonlineshop.serviceinterface.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private ShopService shopService;

    @Resource
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));

    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    @DeleteMapping("/deleteOrder/{orderId}")
    public void deleteOrder(@PathVariable ("orderId") int id){
        orderService.deleteOrder(id);
    }

    @GetMapping("/getAllOrdersByStatus{orderStatus}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByStatus(@PathVariable ("orderStatus") OrderStatus orderStatus){
        return ResponseEntity.ok(orderService.getAllOrdersByStatus(orderStatus));
    }

    @GetMapping("/get-by{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/getOrdersByUserEmail{userEmail}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserEmail(@PathVariable ("userEmail") String userEmail){
        return ResponseEntity.ok(orderService.getOrdersByUserEmail(userEmail));
    }

    @GetMapping("/getOrdersByUserPhoneNumber{userPhoneNumber}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserPhoneNumber(@PathVariable ("userPhoneNumber") String userPhoneNumber){
        return ResponseEntity.ok(orderService.getOrdersByUserPhone(userPhoneNumber));
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.updateOrder(orderDto));
    }


//    Attachment

    @GetMapping("/getAllAttachments")
    public ResponseEntity<List<AttachmentDto>> getAllAttachments(){
        return ResponseEntity.ok(shopService.getAllAttachments());
    }

    @GetMapping("/getAttachmentById")
    public ResponseEntity<AttachmentDto> getAttachmentById(@PathVariable ("id") int id){
        return ResponseEntity.ok(shopService.getAttachmentById(id));
    }

    @PostMapping("/createAttachment")
    public ResponseEntity<AttachmentDto> createAttachment(@RequestBody AttachmentDto attachmentDto){
        return ResponseEntity.ok(shopService.createAttachment(attachmentDto));
    }
    
    @DeleteMapping("/deleteAttachment/{id}")
    public void deleteAttachment(@PathVariable ("id") int id){
        shopService.deleteAttachment(id);
    }
    
    @PutMapping("/updateAttachment")
    public ResponseEntity<AttachmentDto> updateAttachment(@RequestBody AttachmentDto attachmentDto){
        return ResponseEntity.ok(shopService.updateAttachment(attachmentDto));
    }


    // Payment

    @PostMapping ("/makePayment/{id}")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto,
                                                    @PathVariable ("id") int orderId) {
        return ResponseEntity.ok(shopService.createPayment(paymentDto, orderId));
    }

    @GetMapping ("/findAllPayments")
    public ResponseEntity<List<PaymentDto>> getAllPayments(){
        return ResponseEntity.ok(shopService.getAllPayments());
    }

    @GetMapping ("/findPaymentById/{paymentId}")
    public ResponseEntity<PaymentDto> findPaymentById(@PathVariable ("paymentId") Long paymentId){
        return ResponseEntity.ok(shopService.findPaymentById(paymentId));
    }

    @DeleteMapping ("/deletePayment/{orderId}/{paymentId}")
    public void deletePayment(@PathVariable ("orderId") int orderId,@PathVariable ("paymentId") Long paymentId){
        shopService.deletePayment(orderId, paymentId);
    }


    //Discount
    
    @PostMapping ("/createDiscount")
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto discountDto) {
        return ResponseEntity.ok(shopService.createDiscount(discountDto));
    }

    @DeleteMapping ("/deleteDiscount/{id}")
    public void deleteDiscount (@PathVariable ("id") int id){
        shopService.deleteDiscount(id);
    }


    @GetMapping("/getAllDiscounts")
    public ResponseEntity<List<DiscountDto>> getAllDiscounts(){
        return ResponseEntity.ok(shopService.getAllDiscounts());
    }

    @PutMapping("/updateDiscount")
    public ResponseEntity<DiscountDto> updateDiscount(@RequestBody DiscountDto discountDto){
        return ResponseEntity.ok(shopService.updateDiscount(discountDto));
    }


    @PostMapping ("/checkQuantity")
    public ResponseEntity<String> checkProductQuantity (@RequestBody CartDto cartDto){
        System.out.println(shopService.checkProductQuantity(cartDto));
        return ResponseEntity.ok(shopService.checkProductQuantity(cartDto));
    }

}


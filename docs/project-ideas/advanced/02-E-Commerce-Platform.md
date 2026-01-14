# E-Commerce Platform

**Tier:** 3-Advanced  
**Stack:** Next.js Frontend, Spring Boot Backend, PostgreSQL, Payment Gateway Integration

A full-featured e-commerce platform with product catalog, shopping cart, checkout, and order management.

## Learning Goals

- Complex database relationships and transactions
- Payment gateway integration (Stripe, PayPal)
- Inventory management
- Order fulfillment workflow
- Admin dashboard with analytics
- Performance optimization at scale
- Caching strategies
- Search optimization with Elasticsearch

## User Stories

- [ ] User can browse products with filtering and sorting
- [ ] User can search for products
- [ ] User can view product details with images and reviews
- [ ] User can add products to shopping cart
- [ ] User can view and modify shopping cart
- [ ] User can proceed to checkout
- [ ] User can enter shipping and billing information
- [ ] User can pay using multiple payment methods
- [ ] User receives order confirmation email
- [ ] User can view order history and track orders
- [ ] Admin can manage product inventory
- [ ] Admin can view sales analytics and reports

## Bonus Features

- [ ] Wishlist functionality
- [ ] Product recommendations based on browsing/purchase history
- [ ] Coupon/discount code system
- [ ] Subscription products
- [ ] Product variants (size, color, etc.)
- [ ] User reviews and ratings
- [ ] Image optimization and CDN
- [ ] Tax calculation
- [ ] Multi-currency support
- [ ] Inventory alerts when stock is low
- [ ] Abandoned cart recovery emails
- [ ] Admin inventory management dashboard
- [ ] Return/refund management
- [ ] Analytics dashboard with sales trends

## Database Schema

```
User
  - id (PK)
  - email (unique)
  - password (hashed)
  - firstName
  - lastName
  - createdAt

Address
  - id (PK)
  - userId (FK -> User)
  - street
  - city
  - state
  - postalCode
  - country
  - isDefault

Product
  - id (PK)
  - sku (unique)
  - name
  - description
  - price (decimal)
  - discountPrice (nullable)
  - category
  - images (JSON or separate table)
  - stock
  - createdAt

ProductVariant
  - id (PK)
  - productId (FK -> Product)
  - size (optional)
  - color (optional)
  - stock
  - price (optional override)

Cart
  - id (PK)
  - userId (FK -> User)
  - createdAt
  - updatedAt

CartItem
  - id (PK)
  - cartId (FK -> Cart)
  - productId (FK -> Product)
  - variantId (FK -> ProductVariant, nullable)
  - quantity
  - addedAt

Order
  - id (PK)
  - userId (FK -> User)
  - orderNumber (unique)
  - status (pending, confirmed, shipped, delivered, cancelled)
  - subtotal (decimal)
  - tax (decimal)
  - shipping (decimal)
  - total (decimal)
  - shippingAddress (FK -> Address)
  - billingAddress (FK -> Address)
  - paymentMethod
  - transactionId
  - createdAt
  - updatedAt

OrderItem
  - id (PK)
  - orderId (FK -> Order)
  - productId (FK -> Product)
  - variantId (FK -> ProductVariant, nullable)
  - quantity
  - priceAtPurchase (decimal)

Review
  - id (PK)
  - productId (FK -> Product)
  - userId (FK -> User)
  - rating (1-5)
  - title
  - content
  - createdAt

Coupon
  - id (PK)
  - code (unique)
  - discountType (percentage, fixed)
  - discountValue (decimal)
  - minOrderValue (decimal, nullable)
  - maxUses
  - expiresAt
  - createdAt

WishlistItem
  - userId (PK -> User)
  - productId (FK -> Product)
  - addedAt
```

## Spring Boot Service Architecture

```
Controllers:
  - ProductController (list, search, get details)
  - CartController (add, remove, update items)
  - OrderController (create, get history, track)
  - PaymentController (process payment)
  - ReviewController (post, get, delete reviews)
  - AdminProductController (CRUD products)
  - AdminOrderController (manage orders)
  - AdminAnalyticsController (sales, revenue reports)
  - UserController (profiles, addresses)

Services:
  - ProductService (search, filtering, variants)
  - CartService (cart management)
  - OrderService (order creation, status management)
  - PaymentService (payment processing)
  - InventoryService (stock management)
  - EmailService (order confirmations, shipping)
  - ReviewService (CRUD reviews)
  - AnalyticsService (sales data, trends)

Repositories:
  - ProductRepository (with Elasticsearch integration)
  - OrderRepository (with complex queries)
  - CartRepository
  - UserRepository
```

## API Endpoints

```
PRODUCTS:
GET    /api/products              - List products (with pagination/filters)
GET    /api/products/search       - Search products
GET    /api/products/:id          - Get product details
GET    /api/products/:id/reviews  - Get product reviews
POST   /api/products/:id/reviews  - Post review (auth required)

CART:
GET    /api/cart                  - Get current cart
POST   /api/cart/items            - Add to cart
PUT    /api/cart/items/:id        - Update cart item
DELETE /api/cart/items/:id        - Remove from cart
DELETE /api/cart                  - Clear cart

ORDERS:
POST   /api/orders                - Create order
GET    /api/orders                - Get user orders
GET    /api/orders/:id            - Get order details
PUT    /api/orders/:id/status     - Update order status (admin)
GET    /api/orders/:id/track      - Track order

PAYMENT:
POST   /api/payments              - Process payment
GET    /api/payments/:id          - Get payment status
POST   /api/payments/:id/refund   - Refund payment (admin)

WISHLIST:
GET    /api/wishlist              - Get wishlist
POST   /api/wishlist/:productId   - Add to wishlist
DELETE /api/wishlist/:productId   - Remove from wishlist

COUPONS:
GET    /api/coupons/:code         - Validate coupon

ADMIN:
POST   /api/admin/products        - Create product
PUT    /api/admin/products/:id    - Update product
DELETE /api/admin/products/:id    - Delete product
GET    /api/admin/analytics       - Get sales analytics
GET    /api/admin/orders          - List all orders
```

## Useful Links & Resources

- [Stripe API Documentation](https://stripe.com/docs/api)
- [PayPal Developer Documentation](https://developer.paypal.com/)
- [Spring Boot Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html)
- [Elasticsearch with Spring Boot](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/)
- [Next.js Image Optimization](https://nextjs.org/docs/app/building-your-application/optimizing/images)

## Implementation Hints

- Start with basic products and shopping cart
- Implement payment processing carefully (PCI compliance)
- Use database transactions for order creation
- Implement proper inventory locking to prevent overselling
- Cache product catalog but invalidate on updates
- Use async processing for email notifications
- Implement rate limiting on payment endpoints
- Consider Elasticsearch for efficient product search
- Test payment flows thoroughly with test accounts
- Implement proper error handling for payment failures

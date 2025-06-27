package org.torres.backendkitchen.util;

public final class Constants {
    private Constants() {throw new AssertionError("Cannot instantiate Constants class");}

    // Base API path
    public static final String KITCHEN = "/kitchen";

    // Authentication paths
    public static final String AUTH_CONTROLLER = "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";

    //Dish paths
    public static final String DISH_CONTROLLER = "/dishes";


    // Generic paths
    public static final String ID = "/id";
    public static final String GET_ALL = "/getAll";
    public static final String CREATE = "/create";
    public static final String GET_BY_ID = "/getById";
    public static final String UPDATE = "/update";
    public static final String GET_BY_NAME = "/getByName";
    public static final String DELETE = "/delete";


    // Table paths
    public static final String TABLE_CONTROLLER = "/tables";
    public static final String TABLE_NUMBER = "/number";

    // Order paths
    public static final String ORDER_CONTROLLER = "/orders";
    public static final String ORDER_STATE = "/state";
    public static final String ORDER_WAITER = "/waiter";
    public static final String ORDER_PAYMENT = "/payment";

    // OrderDish paths
    public static final String ORDER_DISH_CONTROLLER = "/orderDishes";
    public static final String ORDER_ID = "/orderId";
}


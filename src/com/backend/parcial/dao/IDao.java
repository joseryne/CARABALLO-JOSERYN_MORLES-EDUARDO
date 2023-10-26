package com.backend.parcial.dao;

public interface IDao<T> {
    T registrar(T t);

    T buscarPorId(int id);
}

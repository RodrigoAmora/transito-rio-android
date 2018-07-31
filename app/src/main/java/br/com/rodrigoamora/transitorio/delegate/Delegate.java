package br.com.rodrigoamora.transitorio.delegate;

public interface Delegate<T> {

    void error();
    void success(T t);

}

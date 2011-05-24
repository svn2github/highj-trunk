/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import fj.F;
import fj.Function;
import highj.LC;
import highj.RC;
import highj.__;
import highj.typeclasses.category.Functor;

/**
 * NOTE: You MUST override *either* bimap *or* both first and second (uncurried versions), else you 
 * have an infinite loop
 * @author DGronau
 */
public abstract class BifunctorAbstract<Ctor> implements Bifunctor<Ctor> {

    @Override
    // bimap (Data.Bifunctor)
    public <A, B, C, D> __<Ctor, B, D> bimap(F<A, B> fn1, F<C, D> fn2, __<Ctor, A, C> nestedAC) {
        return first(fn1, second(fn2, nestedAC));
    }

    @Override
    // first (Data.Bifunctor)
    public <A, B, C> __<Ctor, B, C> first(F<A, B> fn, __<Ctor, A, C> nestedAC) {
        return bimap(fn, Function.<C>identity(), nestedAC);
    }

    @Override
    // second (Data.Bifunctor)
    public <A, B, C> __<Ctor, A, C> second(F<B, C> fn, __<Ctor, A, B> nestedAB) {
        return bimap(Function.<A>identity(), fn, nestedAB);
    }
    
    @Override
    //functionality of second as a Functor (with left-curried argumets)   
    public <X> Functor<LC<Ctor, X>> getLCFunctor() {
        return new LCFunctor<Ctor,X>() {
            @Override
            public <A,B> __<Ctor, X, B> fmap(F<A,B> fn, __<Ctor, X, A> nestedA) {
                return BifunctorAbstract.this.second(fn, nestedA);
            }
        };
    }

    @Override
    //functionality of first as a Functor (with right-curried argumets)   
    public <X> Functor<RC<Ctor, X>> getRCFunctor() {
        return new RCFunctor<Ctor,X>() {
            @Override
            public <A,B> __<Ctor, B, X> fmap(F<A,B> fn, __<Ctor, A, X> nestedA) {
                return BifunctorAbstract.this.first(fn, nestedA);
            }
        };
    }

    @Override
    public <A, B, C> F<__<Ctor, A, C>, __<Ctor, B, C>> first(final F<A, B> fn) {
        return new F<__<Ctor, A, C>, __<Ctor, B, C>>() {
            @Override
            public __<Ctor, B, C> f(__<Ctor, A, C> ac) {
                return first(fn, ac);
            }
        };
    }

    @Override
    public <A, B, C> F<__<Ctor, A, B>, __<Ctor, A, C>> second(final F<B, C> fn) {
        return new F<__<Ctor, A, B>, __<Ctor, A, C>>(){
            @Override
            public __<Ctor, A, C> f(__<Ctor, A, B> ab) {
                return second(fn, ab);
            }
        };
    }
    
}

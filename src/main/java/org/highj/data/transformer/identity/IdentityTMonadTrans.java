package org.highj.data.transformer.identity;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.transformer.IdentityT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadTrans;


public interface IdentityTMonadTrans<M> extends IdentityTBind<M>, IdentityTApplicative<M>, MonadTrans<IdentityT.µ, M> {

    @Override
    public Monad<M> get();

    @Override
    public default <A> IdentityT<M, A> lift(_<M, A> nestedA) {
        return new IdentityT<>(nestedA);
    }

    @Override
    public default <A, B> IdentityT<M, B> ap(_<__.µ<IdentityT.µ, M>, Function<A, B>> fn, _<__.µ<IdentityT.µ, M>, A> nestedA) {
        return IdentityTApplicative.super.ap(fn, nestedA);
    }

}

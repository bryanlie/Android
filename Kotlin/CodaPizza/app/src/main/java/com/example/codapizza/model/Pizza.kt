package com.example.codapizza.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.codapizza.model.ToppingPlacement.*

@Parcelize
data class Pizza (
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val size: PizzaSize = PizzaSize.Small
) : Parcelable {
    val price: Double
        get() = 9.99 + toppings.asSequence().sumOf {
            (_, toppingPlacement) -> when (toppingPlacement) {
                Left, Right -> 0.5
                All -> 1.0
            }
        } + size.extraCost


    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if (placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }, size = size
        )
    }

    fun withSize(size: PizzaSize): Pizza {
        return copy(size = size)
    }

}
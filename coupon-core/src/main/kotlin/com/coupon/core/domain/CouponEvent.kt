package com.coupon.core.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
internal class CouponEvent(
    title: String,
    discountPolicy: DiscountPolicy,
    discountRate: Int,
    publishCycle: PublishCycle,
    count: Int,
    expiredDays: Long = 365,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
): BaseEntity() {

    /**
     * 쿠폰 행사명
     */
    var title: String = title
        set(value) {
            if (value.isBlank()) return
            field = value
        }

    /**
     * 할인 정책
     */
    @Embedded
    private val discount: Discount = Discount(discountPolicy, discountRate)

    /**
     * 쿠폰 발행 주기
     */
    @Enumerated(value = EnumType.STRING)
    val publishCycle: PublishCycle = publishCycle

    /**
     * 발행 갯수
     */
    var count: Int = count
        set(value) {
            if (value < 0) return
            field = value
        }


    /**
     * 발행일로부터 만료일까지 일수. 기본 365일
     */
    val expiredDays: Long = expiredDays

    /**
     * 행사 시작일
     */
    var startDateTime = startDateTime
        set(value) {
            if (field.isAfter(LocalDateTime.now())) return
            field = value
        }

    /**
     * 행사 종료일
     */
    var endDateTime = endDateTime
        set(value) {
            if (field.isBefore(LocalDateTime.now())) return
            field = value
        }

    fun getDiscountPolicy(): DiscountPolicy = discount.type

    fun getDiscountRate(): Int = discount.rate
}
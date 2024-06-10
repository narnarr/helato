package dev.nars.helato.entity

import dev.nars.helato.enumeration.Gender
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val username: String,

    var password: String,

    var mobilePhoneNum: String,

    var extraPhoneNum: String?,

    var birthDate: LocalDate?,

    @Enumerated(EnumType.STRING)
    var gender: Gender?

): BaseEntity()
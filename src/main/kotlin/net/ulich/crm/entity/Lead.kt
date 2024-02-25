package net.ulich.crm.entity

import io.jmix.core.DeletePolicy
import io.jmix.core.annotation.DeletedBy
import io.jmix.core.annotation.DeletedDate
import io.jmix.core.entity.annotation.JmixGeneratedValue
import io.jmix.core.entity.annotation.OnDelete
import io.jmix.core.entity.annotation.OnDeleteInverse
import io.jmix.core.metamodel.annotation.Composition
import io.jmix.core.metamodel.annotation.InstanceName
import io.jmix.core.metamodel.annotation.JmixEntity
import io.jmix.data.impl.lazyloading.NotInstantiatedList
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.util.*

@JmixEntity
@Table(name = "LEAD_", indexes = [
    Index(name = "IDX_LEAD__CAMPAIGN", columnList = "CAMPAIGN_ID")
])
@Entity(name = "Lead")
open class Lead {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "lead")
    var scheduledEmails: MutableList<ScheduledEmail> = NotInstantiatedList()

    @Column(name = "EMAIL", nullable = false, length = 512)
    @NotNull
    var email: String? = null

    @Column(name = "PHONE_NUMBER")
    var phoneNumber: String? = null

    @Column(name = "MOBILE_PHONE_NUMBER")
    var mobilePhoneNumber: String? = null

    @Column(name = "GENDER", nullable = false)
    @NotNull
    private var gender: String? = null

    @InstanceName
    @Column(name = "LAST_NAME", length = 1024)
    var lastName: String? = null

    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "CAMPAIGN_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var campaign: Campaign? = null

    @Column(name = "VERSION", nullable = false)
    @Version
    var version: Int? = null

    @CreatedBy
    @Column(name = "CREATED_BY")
    var createdBy: String? = null

    @CreatedDate
    @Column(name = "CREATED_DATE")
    var createdDate: OffsetDateTime? = null

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    var lastModifiedBy: String? = null

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    var lastModifiedDate: OffsetDateTime? = null

    @DeletedBy
    @Column(name = "DELETED_BY")
    var deletedBy: String? = null

    @DeletedDate
    @Column(name = "DELETED_DATE")
    var deletedDate: OffsetDateTime? = null

    fun getGender(): Gender? = gender?.let { Gender.fromId(it) }

    fun setGender(gender: Gender?) {
        this.gender = gender?.id
    }

    fun salutation(): String {
        return when (gender) {
            Gender.MR.id -> "Sehr geehrter Herr ${lastName}"
            Gender.MRS.id -> "Sehr geehrte Frau ${lastName}"
            else -> "Sehr geehrte Damen und Herren"
        }
    }
}
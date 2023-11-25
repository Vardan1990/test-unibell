package com.test.unibell.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.unibell.enums.ContactsType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "client_contacts")
@NoArgsConstructor
@AllArgsConstructor
public class ClientContacts {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Id
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    @Column(name = "created", updatable = false, nullable = false)
    @Getter
    private LocalDateTime created;

    @Column(name = "contact")
    private String contact;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "contacts_type")
    private ContactsType contactsType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ClientContacts that = (ClientContacts) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(created, that.created)
                .append(contact, that.contact)
                .append(contactsType, that.contactsType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(created)
                .append(contact)
                .append(contactsType)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ClientContacts{" +
                "id=" + id +
                ", created=" + created +
                ", contact='" + contact + '\'' +
                ", contactsType=" + contactsType +
                '}';
    }
}

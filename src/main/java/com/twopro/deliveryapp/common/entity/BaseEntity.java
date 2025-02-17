package com.twopro.deliveryapp.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @CreatedBy
    private String created_by;

    @LastModifiedBy
    private String updated_by;

    private LocalDateTime deleted_at;
    private String deleted_by;

    /**
     * 엔티티 삭제할 경우 호출하기
     */
    public void delete() {
        if (this.deleted_at != null) {
            throw new IllegalStateException("이미 삭제된 엔티티입니다.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
//            throw new RuntimeException("삭제 권한이 없습니다."); // security 설정 전에는 예외 터지므로 주석
        }

        this.deleted_at = LocalDateTime.now();
        this.deleted_by = authentication.getName();
    }
}

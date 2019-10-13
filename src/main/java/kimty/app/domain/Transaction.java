package kimty.app.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_t")
    private LocalDate dateT;

    @Column(name = "montant", precision = 21, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_r")
    private LocalDate dateR;

    @Column(name = "commission")
    private Integer commission;

    @Column(name = "comm_system")
    private Integer commSystem;

    @Column(name = "comm_exp")
    private Integer commExp;

    @Column(name = "tax_2")
    private Integer tax2;

    @Column(name = "statut")
    private String statut;

    @Column(name = "code")
    private String code;

    @Column(name = "commretrait")
    private Integer commretrait;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateT() {
        return dateT;
    }

    public Transaction dateT(LocalDate dateT) {
        this.dateT = dateT;
        return this;
    }

    public void setDateT(LocalDate dateT) {
        this.dateT = dateT;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public Transaction montant(BigDecimal montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDateR() {
        return dateR;
    }

    public Transaction dateR(LocalDate dateR) {
        this.dateR = dateR;
        return this;
    }

    public void setDateR(LocalDate dateR) {
        this.dateR = dateR;
    }

    public Integer getCommission() {
        return commission;
    }

    public Transaction commission(Integer commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Integer getCommSystem() {
        return commSystem;
    }

    public Transaction commSystem(Integer commSystem) {
        this.commSystem = commSystem;
        return this;
    }

    public void setCommSystem(Integer commSystem) {
        this.commSystem = commSystem;
    }

    public Integer getCommExp() {
        return commExp;
    }

    public Transaction commExp(Integer commExp) {
        this.commExp = commExp;
        return this;
    }

    public void setCommExp(Integer commExp) {
        this.commExp = commExp;
    }

    public Integer getTax2() {
        return tax2;
    }

    public Transaction tax2(Integer tax2) {
        this.tax2 = tax2;
        return this;
    }

    public void setTax2(Integer tax2) {
        this.tax2 = tax2;
    }

    public String getStatut() {
        return statut;
    }

    public Transaction statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCode() {
        return code;
    }

    public Transaction code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCommretrait() {
        return commretrait;
    }

    public Transaction commretrait(Integer commretrait) {
        this.commretrait = commretrait;
        return this;
    }

    public void setCommretrait(Integer commretrait) {
        this.commretrait = commretrait;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", dateT='" + getDateT() + "'" +
            ", montant=" + getMontant() +
            ", dateR='" + getDateR() + "'" +
            ", commission=" + getCommission() +
            ", commSystem=" + getCommSystem() +
            ", commExp=" + getCommExp() +
            ", tax2=" + getTax2() +
            ", statut='" + getStatut() + "'" +
            ", code='" + getCode() + "'" +
            ", commretrait=" + getCommretrait() +
            "}";
    }
}

import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  isSaving: boolean;
  dateTDp: any;
  dateRDp: any;

  editForm = this.fb.group({
    id: [],
    dateT: [],
    montant: [],
    dateR: [],
    commission: [],
    commSystem: [],
    commExp: [],
    tax2: [],
    statut: [],
    code: [],
    commretrait: []
  });

  constructor(protected transactionService: TransactionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
    });
  }

  updateForm(transaction: ITransaction) {
    this.editForm.patchValue({
      id: transaction.id,
      dateT: transaction.dateT,
      montant: transaction.montant,
      dateR: transaction.dateR,
      commission: transaction.commission,
      commSystem: transaction.commSystem,
      commExp: transaction.commExp,
      tax2: transaction.tax2,
      statut: transaction.statut,
      code: transaction.code,
      commretrait: transaction.commretrait
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id']).value,
      dateT: this.editForm.get(['dateT']).value,
      montant: this.editForm.get(['montant']).value,
      dateR: this.editForm.get(['dateR']).value,
      commission: this.editForm.get(['commission']).value,
      commSystem: this.editForm.get(['commSystem']).value,
      commExp: this.editForm.get(['commExp']).value,
      tax2: this.editForm.get(['tax2']).value,
      statut: this.editForm.get(['statut']).value,
      code: this.editForm.get(['code']).value,
      commretrait: this.editForm.get(['commretrait']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

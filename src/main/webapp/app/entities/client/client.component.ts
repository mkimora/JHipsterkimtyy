import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IClient } from 'app/shared/model/client.model';
import { AccountService } from 'app/core/auth/account.service';
import { ClientService } from './client.service';

@Component({
  selector: 'jhi-client',
  templateUrl: './client.component.html'
})
export class ClientComponent implements OnInit, OnDestroy {
  clients: IClient[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected clientService: ClientService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.clientService
      .query()
      .pipe(
        filter((res: HttpResponse<IClient[]>) => res.ok),
        map((res: HttpResponse<IClient[]>) => res.body)
      )
      .subscribe((res: IClient[]) => {
        this.clients = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInClients();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IClient) {
    return item.id;
  }

  registerChangeInClients() {
    this.eventSubscriber = this.eventManager.subscribe('clientListModification', response => this.loadAll());
  }
}

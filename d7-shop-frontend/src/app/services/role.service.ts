import { Injectable } from '@angular/core';
import { environment } from '../enviroments/enviroment';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class RoleService {
  private apiGetRole = `${environment.apiBaseUrl}/roles`;
  constructor(private http: HttpClient) {}
  getRoles(): Observable<any> {
    return this.http.get<any[]>(this.apiGetRole);
  }
}

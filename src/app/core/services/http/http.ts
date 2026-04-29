import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root',
})
export class HttpService {
    private apiUtrl: string = environment.API_URL;


  constructor(private readonly http: HttpClient) {}

   get<T>(route: string, params?:HttpParams): Observable<T> {
    return this.http.get<T>(`${this.apiUtrl}${route}`,{ params });
   }

}


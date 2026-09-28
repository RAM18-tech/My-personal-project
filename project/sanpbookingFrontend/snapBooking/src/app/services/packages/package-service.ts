import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';





export interface TravelPackage {
  id?: number;
  name: string;
  destination: string;
  duration: string;
  price: number;
  description: string;
  imageUrl: string;
}


@Injectable({
  providedIn: 'root'
})
export class PackageService {

  private apiUrl = 'http://localhost:8000/package-api/packages';

  constructor(private http: HttpClient) { }

  getPackages(): Observable<TravelPackage[]> {
    return this.http.get<TravelPackage[]>(this.apiUrl);
  }

  getPackageById(id: string): Observable<TravelPackage> {
    return this.http.get<TravelPackage>(`${this.apiUrl}/${id}`);
  }


  updatePackage(id: number, updatedPackage: TravelPackage): Observable<TravelPackage> {
    return this.http.put<TravelPackage>(`${this.apiUrl}/${id}`, updatedPackage);
  }

}

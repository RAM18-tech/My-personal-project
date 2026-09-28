import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { PackageService, TravelPackage } from '../../services/packages/package-service';

@Component({
  selector: 'app-packages-list',
  imports: [FormsModule, CurrencyPipe, CommonModule, RouterLink],
  templateUrl: './packages-list.html',
  styleUrl: './packages-list.scss'
})
export class PackagesList implements OnInit{

  public searchTerm: string = '';
  public selectedDestination: string = '';
  public maxPrice: number = 5000;
  public selectedDuration: number = 0;
  public sortBy: string = 'default';

  public currentPage: number = 1;
  public itemsPerPage: number = 6;

  private allPackages: TravelPackage[] = [];
  public filteredPackages: TravelPackage[] = [];
  public uniqueDestinations: string[] = [];
  public priceRange = { min: 0, max: 5000 };

  constructor(private packageService: PackageService) { }

  ngOnInit(): void {
    this.loadPackages();
  }

  loadPackages(): void {
    this.packageService.getPackages().subscribe({
      next: (packages) => {
        this.allPackages = packages;
        this.filteredPackages = packages;
        this.setupFilterControls();
      },
      error: (err) => { }
    });
  }

  setupFilterControls(): void {
    const destinations = this.allPackages.map(p => p.destination);
    this.uniqueDestinations = [...new Set(destinations)].sort();

    const maxPriceFromData = Math.max(...this.allPackages.map(p => p.price));
    if (maxPriceFromData > 0) {
      this.priceRange.max = maxPriceFromData;
      this.maxPrice = maxPriceFromData;
    }
  }

  applyFilters(): void {
    this.currentPage = 1; 
    let packages = [...this.allPackages];
    const term = this.searchTerm.toLowerCase();

    if (term) {
      packages = packages.filter(pkg =>
        pkg.name.toLowerCase().includes(term) ||
        pkg.description.toLowerCase().includes(term)
      );
    }

    if (this.selectedDestination) {
      packages = packages.filter(pkg => pkg.destination === this.selectedDestination);
    }

    packages = packages.filter(pkg => pkg.price <= this.maxPrice);

    if (this.selectedDuration > 0) {
      if (this.selectedDuration === 11) {
        packages = packages.filter(pkg => this.getDaysFromDuration(pkg.duration) > 10);
      } else {
        packages = packages.filter(pkg => this.getDaysFromDuration(pkg.duration) <= this.selectedDuration);
      }
    }

    if (this.sortBy === 'priceAsc') {
      packages.sort((a, b) => a.price - b.price);
    } else if (this.sortBy === 'priceDesc') {
      packages.sort((a, b) => b.price - a.price);
    } else if (this.sortBy === 'nameAsc') {
      packages.sort((a, b) => a.name.localeCompare(b.name));
    }

    this.filteredPackages = packages;
  }

  resetFilters(): void {
    this.searchTerm = '';
    this.selectedDestination = '';
    this.maxPrice = this.priceRange.max;
    this.selectedDuration = 0;
    this.sortBy = 'default';
    this.applyFilters();
  }

  public get totalPages(): number {
    return Math.ceil(this.filteredPackages.length / this.itemsPerPage);
  }

  public setPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  public get paginatedPackages(): TravelPackage[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.filteredPackages.slice(startIndex, endIndex);
  }

  private getDaysFromDuration(duration: string): number {
    const match = duration.match(/(\d+)\s*Days/i);
    return match ? parseInt(match[1], 10) : 0;
  }


}

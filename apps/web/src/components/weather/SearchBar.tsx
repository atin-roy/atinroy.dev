import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";

export default function SearchBar() {
    return (
        <div className="relative w-full max-w-xl">
            {/* The Input */}
            <input
                type="text"
                className="w-full h-14 rounded-full bg-gray-200 pl-8 pr-14 text-gray-900 focus:bg-white focus:ring-1 focus:ring-blue-500 outline-none transition-all border border-transparent focus:border-blue-500 text-xl"
                placeholder="Search"
            />

            {/* The Right-Side Icon Container */}
            <div className="absolute inset-y-0 right-0 flex items-center pr-4">
                <button
                    className=" p-1 rounded-full transition-colors"
                    aria-label="Search"
                >
                    <MagnifyingGlassIcon className="h-6 w-6 text-gray-700 text-2xl" />
                </button>
            </div>
        </div>
    );
}

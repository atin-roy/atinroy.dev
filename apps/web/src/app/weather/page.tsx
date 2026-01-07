import CalendarButton from "@/components/weather/CalendarButton";
import InfoTab from "@/components/weather/InfoTab";
import MainCard from "@/components/weather/MainCard";
import SearchBar from "@/components/weather/SearchBar";
import SecondaryCard from "@/components/weather/SecondaryCard";

export default function WeatherPage() {
    return (
        <div className="flex flex-col gap-5">
            <div className="flex gap-5 justify-center mt-4">
                <SearchBar />
                <CalendarButton />
            </div>
            <div className="flex gap-5 justify-center">
                <MainCard />
                <MainCard />
            </div>
            <div className="">
                <div className="flex flex-col gap-5 items-center">
                    <SecondaryCard />
                    <SecondaryCard />
                    <SecondaryCard />
                </div>
            </div>
        </div>
    );
}

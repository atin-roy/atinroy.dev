import { DM_Sans, Bricolage_Grotesque } from "next/font/google";
import styles from "./weather.module.css";

const dmSans = DM_Sans({
    subsets: ["latin"],
    weight: ["300", "400", "500", "600", "700"],
    variable: "--font-dm-sans",
});

const bricolage = Bricolage_Grotesque({
    subsets: ["latin"],
    weight: ["400", "600", "700"],
    variable: "--font-bricolage",
});

export default function WeatherLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <>
            <div
                className={`${dmSans.variable} ${bricolage.variable} ${styles.weatherWrapper}`}
            >
                {children}
            </div>
        </>
    );
}
